package com.example.books.adaptadores;                  // package dos adaptadores

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.databinding.ItemGrelhaLivroBinding; // gerada a partir de item_grelha_livro.xml
import com.example.books.modelo.Livro;

import java.util.ArrayList;

// 9.1 Adaptador próprio para a grelha
public class GrelhaRecyclerAdaptador extends RecyclerView.Adapter<GrelhaRecyclerAdaptador.ItemViewHolder> {

    private final ArrayList<Livro> listaLivros;         // livros a mostrar
    private final OnLivroClickListener listener;        // a mesma interface da lista

    public GrelhaRecyclerAdaptador(ArrayList<Livro> listaLivros, OnLivroClickListener listener) {
        this.listaLivros = listaLivros;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGrelhaLivroBinding binding =
                ItemGrelhaLivroBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(listaLivros.get(position));         // livro desta posição
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemGrelhaLivroBinding binding;

        public ItemViewHolder(ItemGrelhaLivroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Livro livro) {
            binding.ivCapa.setImageResource(livro.getCapa()); // 9.2 só a capa
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onLivroClick(livro);       // clicar na capa também abre os detalhes
                }
            });
        }
    }
}