package mage.cards.t;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TuinvaleTreefolk extends AdventureCard {

    public TuinvaleTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TREEFOLK, SubType.DRUID}, "{5}{G}",
                "Oaken Boon",
                new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Tuinvale Treefolk
        this.getLeftHalfCard().setPT(6, 5);

        // Oaken Boon
        // Put two +1/+1 counters on target creature.
        this.getRightHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private TuinvaleTreefolk(final TuinvaleTreefolk card) {
        super(card);
    }

    @Override
    public TuinvaleTreefolk copy() {
        return new TuinvaleTreefolk(this);
    }
}
