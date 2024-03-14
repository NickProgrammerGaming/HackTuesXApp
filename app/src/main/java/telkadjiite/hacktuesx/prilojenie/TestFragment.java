package telkadjiite.hacktuesx.prilojenie;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestFragment extends Fragment
{
    private EditText searchEdt;
    private ImageView searchIV;
    private RecyclerView categoryRV, wallpaperRV;
    private ProgressBar loadingPB;

    private ArrayList<String> wallpaperArrayList;

    Activity activity;

    View parentHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        activity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_test, container, false);

        super.onCreate(savedInstanceState);
//        activity.setContentView(R.layout.wallpaper_main);
        searchEdt = parentHolder.findViewById(R.id.EditSearch);
        searchIV = parentHolder.findViewById(R.id.IVSearch);
        categoryRV = parentHolder.findViewById(R.id.RVCategory);
        wallpaperRV = parentHolder.findViewById(R.id.RVWallpapers);
        loadingPB = parentHolder.findViewById(R.id.PBLoading);
        wallpaperArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        categoryRV.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
        wallpaperRV.setLayoutManager(gridLayoutManager);



        searchIV.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {

                String searchStr = searchEdt.getText().toString();

                if (searchStr.isEmpty())
                {

                    Toast.makeText(activity, "Please enter your search query" , Toast.LENGTH_SHORT).show();

                }
                else
                {

                    getWallpapersByCategory(searchStr);

                }

            }

        });
        return parentHolder;
    }

    private void getWallpapersByCategory(String category)
    {

        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query=" + category + "&per_page=30&page=1";
    }

//        return inflater.inflate(R.layout.wallpaper_main, container, false);

}