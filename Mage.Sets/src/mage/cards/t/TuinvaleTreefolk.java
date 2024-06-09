package mage.cards.t;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{G}", "Oaken Boon", "{3}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Oaken Boon
        // Put two +1/+1 counters on target creature.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private TuinvaleTreefolk(final TuinvaleTreefolk card) {
        super(card);
    }

    @Override
    public TuinvaleTreefolk copy() {
        return new TuinvaleTreefolk(this);
    }
}
