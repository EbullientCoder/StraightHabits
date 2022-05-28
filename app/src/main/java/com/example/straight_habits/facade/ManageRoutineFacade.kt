package com.example.straight_habits.facade

import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.models.RoutineModel


class ManageRoutineFacade {
    companion object{
        //Create the Habits List
        fun createRoutineList() : MutableList<RoutineBean>{
            //Habits List
            var routineList : MutableList<RoutineBean> = arrayListOf()


            //Morning-------------------------------------------------------------------------------
            //Good Morning
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
                    0,
                    "Andare al Bagno",
                    "E' abbastanza esplicativo così credo.",
                    "Morning",
                    "8:20",
                    "8:30",
                    false,
                    false))

            //Smalto per le Unghie
            routineList.add(
                RoutineBean(
                    0,
                    "Smalto Amaro",
                    "Mettere lo smalto amaro per le unghie.",
                    "Morning",
                    "8:30",
                    "8:35",
                    false,
                    false))

            //Base Flor
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
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
            routineList.add(
                RoutineBean(
                    0,
                    "Borsa Palestra",
                    "Svuotare la borsa della palestra.",
                    "Evening",
                    "22:30",
                    "22:35",
                    false,
                    false)
            )



            return routineList
        }

        //Create Diet Routine
        fun createRoutineDietList(): MutableList<RoutineBean>{
            //Diet List
            var diet: MutableList<RoutineBean> = ArrayList()

            //Monday & Thursday---------------------------------------------------------------------
            //Breakfast
            diet.add(
                RoutineBean(
                    0,
                    "Colazione",
                    "- Caffè + Latte di Cocco\n- Due fette biscottate + Marmellata (25g)",
                "Diet",
                "8:35",
                "8:50",
                false,
                false))
            //Mid Morning Snack
            diet.add(
                RoutineBean(
                    0,
                    "Snack di metà Colazione",
                    "- Pane (40g)\n- Fesa di Tacchino (60g)",
                    "Diet",
                    "11:00",
                    "11:15",
                    false,
                    false))
            //Launch
            diet.add(
                RoutineBean(
                    0,
                    "Pranzo",
                    "- Pasta + Pomodorini + Tonno al Naturale (80g)",
                    "Diet",
                    "13:00",
                    "13:30",
                    false,
                    false))
            //Mid Evening Snack
            diet.add(
                RoutineBean(
                    0,
                    "Snack",
                    "- Yogurt alla frutta + banana\n- Mandorle (10)",
                    "Diet",
                    "17:00",
                    "17:15",
                    false,
                    false))
            //Dinner
            diet.add(
                RoutineBean(
                    0,
                    "Cena",
                    "- Fettina di Bovino (200g)\n- Pane (60g)",
                    "Diet",
                    "20:00",
                    "20:30",
                    false,
                    false))


            //Tuesday & Friday----------------------------------------------------------------------
            //Launch
            diet.add(
                RoutineBean(
                    0,
                    "Pranzo",
                    "- Pasta + Verdure + Salmone Affumicato (100g)",
                    "Diet",
                    "13:00",
                    "13:30",
                    false,
                    false))
            //Dinner
            diet.add(
                RoutineBean(
                    0,
                    "Cena",
                    "- Petto di Pollo (200g)\n- Pane (60g)",
                    "Diet",
                    "20:00",
                    "20:30",
                    false,
                    false))


            //Wednesday & Saturday------------------------------------------------------------------
            //Launch
            diet.add(
                RoutineBean(
                    0,
                    "Pranzo",
                    "- Riso (80g) + Verdure + Uova sode (3)",
                    "Diet",
                    "13:00",
                    "13:30",
                    false,
                    false))
            //Dinner
            diet.add(
                RoutineBean(
                    0,
                    "Cena",
                    "- Bresaola (150g)\n- Pane (60g)",
                    "Diet",
                    "20:00",
                    "20:30",
                    false,
                    false))


            //Sunday--------------------------------------------------------------------------------
            //Launch
            diet.add(
                RoutineBean(
                    0,
                    "Pranzo",
                    "- Pasta (80g) + Passata di Pomodoro\n- Petto di Pollo (100g)",
                    "Diet",
                    "13:00",
                    "13:30",
                    false,
                    false))
            //Dinner
            diet.add(
                RoutineBean(
                    0,
                    "Cena",
                    "- Filetto di Meluzzo (200g)\n- Pane (60g)",
                    "Diet",
                    "20:00",
                    "20:30",
                    false,
                    false))


            return diet
        }




        //Get the Position
        fun getSelectedPosition(routines: MutableList<RoutineBean>) : Int{
            //Search the Selected Habit
            //If habitsList is empty
            if(routines.size == 0)
                return 0

            //If habitsList is not empty
            if(routines.size != 0){
                for(i in 0 until routines.size)
                    if(routines[i].getSelected())
                        return i
            }

            return routines.size - 1
        }


        //Get the Position of the first 'not done' habit
        fun getNotDone(routines: MutableList<RoutineBean>, position: Int): Int{
            //If habitsList is empty
            if(routines.size == 0)
                return 0

            //Search the not done habit
            if(routines.size != 0){
                for(i in position until routines.size)
                    if(!routines[i].getDone())
                        return i
            }

            return routines.size - 1
        }


        //Convert Habit Bean to Model Bean
        fun beanToModel(routine: RoutineBean, day: String) : RoutineModel{
            return RoutineModel(
                routine.getID(),
                routine.getName(),
                routine.getInformation(),
                routine.getCategory(),
                routine.getStartHour(),
                routine.getEndHour(),
                day,
                routine.getSelected(),
                routine.getDone()
            )
        }


        //Reset List
        fun resetHabitsList(routines: MutableList<RoutineModel>){
            //Reset first Habit
            routines[0].setSelected(true)
            routines[0].setDone(false)

            //Reset the other Habits
            for(i in 1 until routines.size){
                routines[i].setSelected(false)
                routines[i].setDone(false)
            }
        }
    }
}