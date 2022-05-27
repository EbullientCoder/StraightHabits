package com.example.straight_habits.facade

import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.models.HabitModel


class ManageHabitsFacade {
    companion object{
        //Create the Habits List
        fun createHabitsList() : MutableList<HabitBean>{
            //Habits List
            var habitsList : MutableList<HabitBean> = arrayListOf()


            //Morning-------------------------------------------------------------------------------
            //Good Morning
            habitsList.add(
                HabitBean(
                    0,
                "Sveglia",
                "Devi svegliarti presto, daje su, sii produttivo!",
                    "Morning",
            "8:00",
                "8:15",
                false,
                false)
            )

            //Get Up and Remove the Cables
            habitsList.add(
                HabitBean(
                    0,
                    "Togliere i Cavi",
                "Alzati e togli i cavi dal cavolo che danno fastidio.",
                    "Morning",
                "8:15",
                "8:15",
                false,
                false)
            )

            //Do the Bed
            habitsList.add(
                HabitBean(
                    0,
                    "Rifare il Letto",
                    "Rifai il letto, daje mpo!",
                    "Morning",
                    "8:15",
                    "8:20",
                    false,
                    false)
            )

            //Bathroom
            habitsList.add(
                HabitBean(
                    0,
                    "Andare al Bagno",
                    "E' abbastanza esplicativo così credo.",
                    "Morning",
                    "8:20",
                    "8:30",
                    false,
                    false))

            //Smalto per le Unghie
            habitsList.add(
                HabitBean(
                    0,
                    "Smalto Amaro",
                    "Mettere lo smalto amaro per le unghie.",
                    "Morning",
                    "8:30",
                    "8:35",
                    false,
                    false))

            //Base Flor
            habitsList.add(
                HabitBean(
                    0,
                    "Scendere le Scale",
                    "Per forza",
                    "Morning",
                    "8:35",
                    "8:35",
                    false,
                    false)
            )

            //Weight Yourself
            habitsList.add(
                HabitBean(
                    0,
                    "Pesarsi alla Bilancia",
                    "Prima o poi dovremmo anche iniziare a farci delle foto. " +
                            "Magari quando sarò presentabile.",
                    "Morning",
                    "8:35",
                    "8:35",
                    false,
                    false)
            )

            //Photo & Weight
            habitsList.add(
                HabitBean(
                    0,
                    "Foto",
                    "Foto del fisico e scrivere il peso.",
                    "Morning",
                    "8:35",
                    "8:35",
                    false,
                    false)
            )

            //Coffee & Breakfast
            habitsList.add(
                HabitBean(
                    0,
                    "Colazione",
                    "Leggere sul micronde la dieta e preparare il cibo per dopo.",
                    "Morning",
                    "8:35",
                    "8:50",
                    false,
                    false)
            )

            //Water
            habitsList.add(
                HabitBean(
                    0,
                    "Bottiglia d'Acqua",
                    "Prendere la bottiglia d'acqua dal frigo.",
                    "Morning",
                    "8:50",
                    "8:50",
                    false,
                    false)
            )

            //Gym Bag
            habitsList.add(
                HabitBean(
                    0,
                    "Borsa della Palestra",
                    "Preparare la borsa della palestra.",
                    "Morning",
                    "8:50",
                    "9:00",
                    false,
                    false
                )
            )


            
            //Afternoon-----------------------------------------------------------------------------
            //Shutdown
            habitsList.add(
                HabitBean(
                    0,
                    "Spegnere il Computer",
                    "Spegnere il computer e preparare lo zaino.",
                    "Afternoon",
                    "12:50",
                    "13:00",
                    false,
                    false)
            )

            //Let's Lunch
            habitsList.add(
                HabitBean(
                    0,
                    "Andare a Mangiare",
                    "Portare giù la bottiglia d'acqua.",
                    "Afternoon",
                    "13:00",
                    "13:05",
                    false,
                    false)
            )




            //Evening-----------------------------------------------------------------------------
            //Gym
            habitsList.add(
                HabitBean(
                    0,
                    "Palestra",
                    "Andare in Palestra.",
                    "Evening",
                    "20:00",
                    "20:00",
                    false,
                    false)
            )

            //Training
            habitsList.add(
                HabitBean(
                    0,
                    "Allenarsi",
                    "Una volta arrivato in palestra cosa vuoi fare?",
                    "Evening",
                    "20:30",
                    "22:00",
                    false,
                    false)
            )

            //Gym Bag
            habitsList.add(
                HabitBean(
                    0,
                    "Borsa Palestra",
                    "Svuotare la borsa della palestra.",
                    "Evening",
                    "22:30",
                    "22:35",
                    false,
                    false)
            )



            return habitsList
        }


        //Get the Position
        fun getSelectedPosition(habits: MutableList<HabitBean>) : Int{
            //Search the Selected Habit
            //If habitsList is empty
            if(habits.size == 0)
                return 0

            //If habitsList is not empty
            if(habits.size != 0){
                for(i in 0 until habits.size)
                    if(habits[i].getSelected())
                        return i
            }

            return habits.size - 1
        }


        //Get the Position of the first 'not done' habit
        fun getNotDone(habits: MutableList<HabitBean>, position: Int): Int{
            //If habitsList is empty
            if(habits.size == 0)
                return 0

            //Search the not done habit
            if(habits.size != 0){
                for(i in position until habits.size)
                    if(!habits[i].getDone())
                        return i
            }

            return habits.size - 1
        }


        //Convert Habit Bean to Model Bean
        fun beanToModel(habit: HabitBean, day: String) : HabitModel{
            return HabitModel(
                habit.getID(),
                habit.getName(),
                habit.getInformation(),
                habit.getCategory(),
                habit.getStartHour(),
                habit.getEndHour(),
                day,
                habit.getSelected(),
                habit.getDone()
            )
        }


        //Reset List
        fun resetHabitsList(habits: MutableList<HabitModel>){
            //Reset first Habit
            habits[0].setSelected(true)
            habits[0].setDone(false)

            //Reset the other Habits
            for(i in 1 until habits.size){
                habits[i].setSelected(false)
                habits[i].setDone(false)
            }
        }
    }
}