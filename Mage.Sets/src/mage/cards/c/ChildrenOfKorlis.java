
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author LevelX2
 */
public final class ChildrenOfKorlis extends CardImpl {

    public ChildrenOfKorlis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Children of Korlis: You gain life equal to the life you've lost this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainLifeEffect(new SourceControllerLostLifeCount(),"You gain life equal to the life you've lost this turn"), 
                new SacrificeSourceCost()));
    }

    private ChildrenOfKorlis(final ChildrenOfKorlis card) {
        super(card);
    }

    @Override
    public ChildrenOfKorlis copy() {
        return new ChildrenOfKorlis(this);
    }
}

class SourceControllerLostLifeCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            return watcher.getLifeLost(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public SourceControllerLostLifeCount copy() {
        return new SourceControllerLostLifeCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "life you've lost this turn";
    }
}
