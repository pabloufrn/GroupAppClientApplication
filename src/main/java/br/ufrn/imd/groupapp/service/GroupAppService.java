package br.ufrn.imd.groupapp.service;

import br.ufrn.imd.groupapp.model.Group;
import br.ufrn.imd.groupapp.model.Message;
import br.ufrn.imd.groupapp.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GroupAppService {
    @GET("group")
    Call<List<Group>> listGroups();

    @POST("group")
    Call<User> createGroup(@Body() User user);

    @POST("group/{id}/user")
    Call<User> joinGroup(@Path("id") Long id, @Body User user);

    @DELETE("user/{id}")
    Call<Void> leaveGroup(@Path("id") Long id);

    @GET("/message")
    Call<List<Message>> getMessages(@Query("group") Long groupId, @Query("from") String from);

    @POST("/message")
    Call<Message> sendMessage(@Body Message message, @Query("userId") Long userId);
}
