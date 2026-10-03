package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class AwakenTheInferno extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter =
            new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AwakenTheInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Awaken the Inferno deals 6 damage to target creature or planeswalker an opponent controls. Put a +1/+1 counter on up to one target creature you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetPermanent(filter)
            .setTargetTag(1).withChooseHint("to damage"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1)
            .setTargetTag(2).withChooseHint("to get a +1/+1 counter"));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private AwakenTheInferno(final AwakenTheInferno card) {
        super(card);
    }

    @Override
    public AwakenTheInferno copy() {
        return new AwakenTheInferno(this);
    }
}
