package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.common.OpponentGraveyardCardCount;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Vernon
 */
public final class SpoilsOfWar extends CardImpl {

    public SpoilsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // X is the number of artifact and/or creature cards in an opponent's graveyard as you cast this spell.
        // Distribute X +1/+1 counters among any number of target creatures.
        OpponentGraveyardCardCount xValue = OpponentGraveyardCardCount.instance;
        this.getSpellAbility().addEffect(new DistributeCountersEffect(CounterType.P1P1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue));
    }

    private SpoilsOfWar(final SpoilsOfWar card) {
        super(card);
    }

    @Override
    public SpoilsOfWar copy() {
        return new SpoilsOfWar(this);
    }
}

