package br.ufrn.imd.groupapp.service;

import br.ufrn.imd.groupapp.model.Group;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface GroupAppService {
    @GET("group/")
    Call<List<Group>> listGroups();

    @GET("group/{id}")
    Call<Group> selectGroup(@Path("id") Long id);
}
