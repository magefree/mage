package org.mage.test.player.RLagent;

import org.nd4j.linalg.api.ndarray.INDArray;
import mage.abilities.*;
import mage.MageObject;
import mage.abilities.common.PassAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import java.util.*;
import org.apache.log4j.Logger;
import org.nd4j.linalg.factory.Nd4j;
import mage.game.permanent.Battlefield;

public class RepresentedGame {
    protected int numActions;
    protected List<INDArray> actionRepr;
    protected List<INDArray> gameRepr;
    RepresentedGame(int numActions, List<INDArray> gameRepr,List<INDArray> actionRepr){
        this.numActions=numActions;
        this.gameRepr=gameRepr;
        this.actionRepr=actionRepr;
    }
}
