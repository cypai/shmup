package com.pipai.shmup.artemis.components;

import com.artemis.Component;

import java.util.ArrayList;

public class EnemyShotAiComponent extends Component {

    public ArrayList<CircularShotType> shotTypes;

    public int initialDelay;
    public int loopAmount;
    public int loopDelay;
    public int loopTimer;

}
