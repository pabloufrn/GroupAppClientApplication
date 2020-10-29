package br.ufrn.imd.groupapp.service;

import br.ufrn.imd.groupapp.model.Group;
import br.ufrn.imd.groupapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface GroupAppService {
    @GET("group/")
    Call<List<Group>> listGroups();

    @GET("group/{id}")
    Call<Group> selectGroup(@Path("id") Long id);

    @POST("group/{username}")
    Call<User> createGroup(@Path("username") String username, @Body Group group);

    @GET("group/{id}/join/{username}")
    Call<User> joinGroup(@Path("id") Long id, @Path("username") String username);
}
