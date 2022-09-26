package www.nazirdaudo.lojaonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import www.nazirdaudo.lojaonline.Modelo.PopularModel;
import www.nazirdaudo.lojaonline.R;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder>
{
    private Context context;
    private List<PopularModel> popularModelList;

    public PopularAdapters(Context context, List<PopularModel> popularModelList) {
        this.context = context;
        this.popularModelList = popularModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Glide.with(context).load(popularModelList.get(position).getImg_url()).into(holder.popImg);
        holder.nome.setText(popularModelList.get(position).getNome());
        holder.rating.setText(popularModelList.get(position).getRate());
        holder.descricao.setText(popularModelList.get(position).getDescricao());
        holder.disconto.setText(popularModelList.get(position).getDisconto());



    }

    @Override
    public int getItemCount()
    {

        return popularModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popImg;
        TextView nome, descricao, rating, disconto;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            nome = itemView.findViewById(R.id.pop_nome);
            descricao = itemView.findViewById(R.id.pop_descricao);
            rating = itemView.findViewById(R.id.pop_rate);
            disconto = itemView.findViewById(R.id.pop_disconto);
        }
    }
}
