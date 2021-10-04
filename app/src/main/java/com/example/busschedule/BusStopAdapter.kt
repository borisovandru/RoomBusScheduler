package com.example.busschedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.*
/*
BusStopAdapter расширяет универсальный ListAdapter, который принимает
список объектов Schedule и класс BusStopViewHolder для пользовательского интерфейса.
Для BusStopViewHolder передается тип DiffCallback.
Сам класс BusStopAdapter также принимает параметр onItemClicked ().
Эта функция будет использоваться для обработки навигации, когда элемент выбран на первом экране,
но для второго экрана передается пустая функция.
 */

class BusStopAdapter(private val onItemClicked: (Schedule) -> Unit) :
    ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

//Подобно recycler view adapter, нужен держатель представления(view holder),
//чтобы получить доступ к представлениям(view), созданным из файла макета(xml)
    class BusStopViewHolder(private var binding: BusStopItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
//функция bind () для установки stopNameTextView
        fun bind(schedule: Schedule) {
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text = SimpleDateFormat(
                "h:mm a").format(
                Date(schedule.arrivalTime.toLong() * 1000)
            )
        }
    }

/*
раздувает/наполняет макет и устанвливает onClickListener ()
для вызова onItemClicked () для элемента в текущей позиции.
 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        /*
        DiffCallback объект, который помогает ListAdapter определить,
        какие элементы в новом и старом списках отличаются при обновлении списка
         */
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }
/*
Существует два метода:
    areItemsTheSame () проверяет, является ли объект (или строка в базе данных в моем случае)
        одинаковым, проверяя только идентификатор.

    areContentsTheSame () проверяет, совпадают ли все свойства, а не только идентификатор.

Эти методы позволяют ListAdapter определять, какие элементы были вставлены, обновлены и удалены,
чтобы пользовательский интерфейс мог быть обновлен соответствующим образом.
 */
            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }
}