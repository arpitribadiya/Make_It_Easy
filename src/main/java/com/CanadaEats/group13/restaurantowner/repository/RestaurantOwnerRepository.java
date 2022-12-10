package com.CanadaEats.group13.restaurantowner.repository;


import com.CanadaEats.group13.authentication.model.response.UserDetailsResponseModel;
import com.CanadaEats.group13.database.IDatabaseConnection;
import com.CanadaEats.group13.restaurantowner.dto.MenuItemDto;
import com.CanadaEats.group13.restaurantowner.dto.RestaurantOwnerDto;
import com.CanadaEats.group13.restaurantowner.model.request.MenuItemRequestModel;
import com.CanadaEats.group13.restaurantowner.model.request.MenuRequestModel;
import com.CanadaEats.group13.utils.ApplicationConstants;
import org.springframework.beans.BeanUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantOwnerRepository implements IRestaurantOwnerRepository {

    IDatabaseConnection databaseConnection;
    Connection connection;
    Statement statement;
    ResultSet result;
    public RestaurantOwnerRepository(IDatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<RestaurantOwnerDto> getAllMenus(String restaurantId){
        List<RestaurantOwnerDto> response =  new ArrayList<>();
        try
        {
            connection = databaseConnection.getDatabaseConnection();
            statement = connection.createStatement();
            String getMenus = "SELECT m.MenuId,m.Name, r.RestaurantId, r.Name as RestaurantName FROM CSCI5308_13_DEVINT.Menu as m join CSCI5308_13_DEVINT.Restaurant as r on m.Restaurant_RestaurantId = r.RestaurantId where m.Restaurant_RestaurantId = '" + restaurantId + "'";
            result = statement.executeQuery(getMenus);

            try
            {
                while (result.next()) {
                    RestaurantOwnerDto restaurantOwnerDto = new RestaurantOwnerDto();
                    restaurantOwnerDto.setMenuId(result.getString(ApplicationConstants.MENU_MENUID_COLUMN));
                    restaurantOwnerDto.setName(result.getString(ApplicationConstants.MENU_MENUNAME_COLUMN));
                    restaurantOwnerDto.setRestaurantId(result.getString(ApplicationConstants.RESTAURANT_RESTAURANTID_COLUMN));
                    restaurantOwnerDto.setRestaurantName(result.getString(ApplicationConstants.RESTAURANT_RESTAURANTNAME_COLUMN));
                    response.add(restaurantOwnerDto);
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally{
            try{
                connection.close();
                statement.close();
                result.close();
            }
            catch (Exception ex){
                System.out.println("Exception : RestaurantOwnerRepository - Closing database connection in getAllMenus()");
            }
        }
        return response;
    }

    public boolean addMenu(MenuRequestModel menuRequestModel){
        boolean menuAdded = false;
        try
        {
            connection = databaseConnection.getDatabaseConnection();
            statement = connection.createStatement();
            String getUser = "select * from Menu where Name = '" + menuRequestModel.getName() + "' and Restaurant_RestaurantId ='" + menuRequestModel.getRestaurantId() + "' and Status = 1";
            result = statement.executeQuery(getUser);

            if(result.next() == false)
            {
                String insertUser = "insert into Menu (MenuId, Name, Status, Restaurant_RestaurantId) values (?,?,?,?)";
                PreparedStatement prepStat=connection.prepareStatement(insertUser);
                prepStat.setString(1, menuRequestModel.getMenuId());
                prepStat.setString(2, menuRequestModel.getName());
                prepStat.setInt(3, menuRequestModel.getStatus());
                prepStat.setString(4, menuRequestModel.getRestaurantId());
                int count = prepStat.executeUpdate();

                if(count > 0)
                {
                    menuAdded = true;
                    System.out.println("Success : RestaurantOwnerRepository - addMenu()");
                }
                else
                {
                    System.out.println("Failure : RestaurantOwnerRepository - addMenu()");
                }
            }
            else{
                System.out.println("Failure : Already Menu Present with this Name");
            }
        }
        catch (Exception ex) {
            System.out.println("Exception : RestaurantOwnerRepository - addMenu()");
            System.out.println(ex);
        }
        finally{
            try{
                connection.close();
                statement.close();
                result.close();
            }
            catch (Exception ex){
                System.out.println("Exception : RestaurantOwnerRepository - Closing database connection in addMenu()");
            }
        }
        return menuAdded;
    }

    @Override
    public boolean addMenuItem(MenuItemRequestModel menuItemRequestModel) {
        boolean menuItemAdded = false;
        try
        {
            connection = databaseConnection.getDatabaseConnection();
            statement = connection.createStatement();
            String getUser = "select * from MenuItem where Name = '" + menuItemRequestModel.getName() + "' and Menu_MenuId ='" + menuItemRequestModel.getMenuId() + "' and Status = 1";
            result = statement.executeQuery(getUser);

            if(result.next() == false)
            {
                String insertUser = "insert into MenuItem (MenuItemId, Name, Description, Price, Status, Menu_MenuId) values (?,?,?,?,?,?)";
                PreparedStatement prepStat=connection.prepareStatement(insertUser);
                prepStat.setString(1, menuItemRequestModel.getMenuItemId());
                prepStat.setString(2, menuItemRequestModel.getName());
                prepStat.setString(3, menuItemRequestModel.getDescription());
                prepStat.setInt(4, menuItemRequestModel.getPrice());
                prepStat.setInt(5, menuItemRequestModel.getStatus());
                prepStat.setString(6, menuItemRequestModel.getMenuId());
                int count = prepStat.executeUpdate();

                if(count > 0)
                {
                    menuItemAdded = true;
                    System.out.println("Success : RestaurantOwnerRepository - addMenuItem()");
                }
                else
                {
                    System.out.println("Failure : RestaurantOwnerRepository - addMenuItem()");
                }
            }
            else{
                System.out.println("Failure : Already MenuItem Present with this Name");
            }

        }
        catch (Exception ex) {
            System.out.println("Exception : RestaurantOwnerRepository - addMenuItem()");
            System.out.println(ex);
        }
        finally{
            try{
                connection.close();
                statement.close();
                result.close();
            }
            catch (Exception ex){
                System.out.println("Exception : RestaurantOwnerRepository - Closing database connection in addMenuItem()");
            }
        }
        return menuItemAdded;
    }

    @Override
    public List<MenuItemDto> getMenuItems(String menuId) {
        List<MenuItemDto> response = new ArrayList<>();

        try
        {
            connection = databaseConnection.getDatabaseConnection();
            statement = connection.createStatement();
            String getMenus = "SELECT MenuItemId, Name, Description, Price, Menu_MenuId FROM MenuItem where Menu_MenuId = '" + menuId + "' and Status = 1";
            result = statement.executeQuery(getMenus);

            try
            {
                while (result.next()) {
                    MenuItemDto menuItemDto = new MenuItemDto();
                    menuItemDto.setMenuItemId(result.getString(ApplicationConstants.MENUITEM_MENUITEMID_COLUMN));
                    menuItemDto.setName(result.getString(ApplicationConstants.MENUITEM_NAME_COLUMN));
                    menuItemDto.setDescription(result.getString(ApplicationConstants.MENUITEM_DESCRIPTION_COLUMN));
                    menuItemDto.setPrice(Integer.parseInt(result.getString(ApplicationConstants.MENUITEM_PRICE_COLUMN)));
                    menuItemDto.setMenuId(result.getString(ApplicationConstants.MENUITEM_MENUID_COLUMN));
                    response.add(menuItemDto);
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally{
            try{
                connection.close();
                statement.close();
                result.close();
            }
            catch (Exception ex){
                System.out.println("Exception : RestaurantOwnerRepository - Closing database connection in getMenuItems()");
            }
        }
        return  response;
    }
}
