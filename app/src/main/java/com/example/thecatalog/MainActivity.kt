package com.example.thecatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.thecatalog.fragments.EditCatalogFragment
import com.example.thecatalog.fragments.ViewCatalogFragment
import com.example.thecatalog.models.CatalogCategory
import com.example.thecatalog.models.CatalogItem
import com.example.thecatalog.models.CatalogSubCategory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var pager: ViewPager2
    private lateinit var tab: TabLayout

    private val tabTitles = arrayOf("View", "Edit")

    private val catalog = mutableListOf(
        CatalogCategory("CPU", mutableListOf(
            CatalogSubCategory("Intel", mutableListOf(
                CatalogItem(
                    "Процессор Intel Core i7 12700K, LGA 1700",
                    "Повышенная работоспособность - одна из отличительных особенностей процессора Intel Original Core i7 12700K Soc-1700. Вместе с ним можно использовать память типа DDR5, которая обладает высокой скоростью передачи данных до 4800 Мб/c. Разблокированный множитель позволит точно настроить вычислительную мощность и получить нужную производительность.",
                    "10/10/2021",
                    37990
                ),
                CatalogItem(
                    "Intel Core i9 12900K, LGA 1700",
                    "Благодаря сочетанию современных технологий и интеллектуальных решений процессор Intel Original Core i9 12900K Soc-1700 обеспечивает высокий уровень производительности в любой ситуации. Интегрированная непосредственно в аппаратное программное обеспечение технология Intel Thread Director направляет необходимую рабочую нагрузку туда, где это необходимо. Максимальная температура процессора составляет 100 градусов.",
                    "02/02/2021",
                    56290
                ),
                CatalogItem(
                    "Серверный процессор Intel Xeon E3-1220",
                    "Серверный процессор Intel Xeon E3-1220 v5 OEM [LGA 1151, 4 x 3000 МГц, L2 - 1 МБ, L3 - 8 МБ, 2хDDR3L, DDR4-2133 МГц, TDP 80 Вт]",
                    "06/04/2020",
                    10299
                )
            )),
            CatalogSubCategory("AMD", mutableListOf(
                CatalogItem(
                    "AMD Ryzen 5 3600",
                    "AMD Ryzen 5 3600 – шестиядерный процессор, который устанавливается в геймерские и рабочие компьютеры. Модель отвечает за высокую производительность системы, ее быструю загрузку, а также обеспечивает эффективность многозадачного режима. Устройство поддерживает систему Wraith Stealth, отвечающую за охлаждение и предотвращающую перегрев.",
                    "08/05/2020",
                    20990
                )
            )),
        )),
        CatalogCategory("GPU", mutableListOf(
            CatalogSubCategory("Nvidia", mutableListOf(
                CatalogItem(
                    "Palit NVIDIA GeForce RTX 3060",
                    "Отлично проявляет себя в играх мощная видеокарта PALIT NVIDIA GeForce RTX 3060 с интерфейсом PCI-E 4.0. Обеспечивает высокую производительность игрового процесса, функционирует с частотой графического процессора 1320 МГц, доходящей до 1777 МГц в режиме Boost. При объемах памяти 12 Гб ее частота равна 15000 МГц. Разрядность шины видеопамяти составляет 192 bit. Максимальное поддерживаемое разрешение 7680 х 4320. Видеокарта PALIT NVIDIA GeForce RTX 3060 поддерживает технологии DirectX 12, OpenGL 4.6 и трассировку лучей. Используются разъемы HDMI в версии 2.1 и Display Port в версии 1.4а, а также разъем для обязательного дополнительного питания 8 pin. Работает практически бесшумно.",
                    "04/10/2020",
                    88590
                ),
                CatalogItem(
                    "RTX3060/ti",
                    "Видеокарта RTX3060/ti, 12 Гб, с независимым дисплеем, для настольных игр, с блокировкой видеокарты, Hashrate 3060",
                    "12/12/2021",
                    90905
                )
            )),
        )),
        CatalogCategory("Displays", mutableListOf(
            CatalogSubCategory("4K monitors", mutableListOf(
                CatalogItem(
                    "Монитор Xiaomi Redmi Display RMMNT27NF 27",
                    "Описание",
                    "13/03/2019",
                    17110
                ),
                CatalogItem(
                    "LG Ergo 34 дюйма 34WN780-B",
                    "UltraWide IPS монитор LG Ergo 34 дюйма 34WN780-B",
                    "12/12/2020",
                    43599
                )
            )),
            CatalogSubCategory("1080p monitors", mutableListOf(
                CatalogItem(
                    "BenQ GW2780 27",
                    "Дизайн монитора BenQ GW2780 диагональю 27 дюймов с ультратонкой рамкой и скрытой кабельной панелью сочетает в себе эстетику и лаконичность. Передовая интеллектуальная технология регулировки яркости Brightness Intelligence эффективно дополняет эксклюзивную технологию защиты зрения BenQ Eye-Care™, а также технологии уменьшения синего света и устранения мерцания. В совокупности, они обеспечивают еще больший зрительный комфорт даже при длительном просмотре и гарантируют различимость самых мелких деталей изображения при любом освещении. Благодаря идеальной комбинации технологий LED и IPS, монитор GW2780 — это новая веха визуального восприятия, за которой передача цвета становится еще точнее, оттенки черного — глубже, контрастность — выше, а детали — четче.",
                    "05/06/2018",
                    6717
                )
            ))
        ))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = catalogHolder
        val adapter = ScreenSlidePagerAdapter(this)
        pager.adapter = adapter

        tab = tlTabs

        TabLayoutMediator(tab, pager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            when(position) {
                0 -> return ViewCatalogFragment(catalog)
                1 -> return EditCatalogFragment(catalog)
            }
            return Fragment()
        }
    }
}