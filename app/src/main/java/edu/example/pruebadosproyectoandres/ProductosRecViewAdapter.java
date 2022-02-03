package edu.example.pruebadosproyectoandres;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import edu.example.pruebadosproyectoandres.R;

import logica.producto.Producto;

import java.io.File;
import java.util.ArrayList;

public class ProductosRecViewAdapter extends RecyclerView.Adapter<ProductosRecViewAdapter.ViewHolder> {

    private ArrayList<Producto> productos;
    private ViewHolder.OnNoteListener mOnNoteListener;

    private Context context;

    public ProductosRecViewAdapter(Context context) {
        this.context = context;
        productos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view, mOnNoteListener);
        return holder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtName.setText(productos.get(position).getNombre());
        if(this.getProductos().get(position).isPrecioVisible())
            holder.txtPrice.setText("$" + productos.get(position).getPrecio());
        else
            holder.txtPrice.setText(" - - -");

        File imgFile = new File(productos.get(position).getUbicImg());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.image.setImageBitmap(myBitmap);
        }

        Glide.with(context)
                .asBitmap()
                .load(productos.get(position).getUbicImg())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        if(productos == null || productos.isEmpty())
            return 0;
        return productos.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProductos(ArrayList<Producto> productos, ViewHolder.OnNoteListener onNoteListener){
        this.productos = productos;
        this.mOnNoteListener = onNoteListener;
        this.notifyDataSetChanged();
    }

    public ArrayList<Producto> getProductos(){
        return productos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName, txtPrice;
        private ImageView image;
        private CardView parent;
        private final Context context;
        public OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.context = itemView.getContext();
            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            image = itemView.findViewById(R.id.image);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onProductClick(getAdapterPosition());
        }

        public interface OnNoteListener{
            void onProductClick(int position);
        }
    }
}
