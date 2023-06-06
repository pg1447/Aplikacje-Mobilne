package pl.app.newton.data;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewtonApi {

    @GET("api/v2/{operation}/{expression}")
    Single<ApiResponse> performOperation(@Path(value = "operation") String operation, @Path(value = "expression", encoded = true) String expression);
}
