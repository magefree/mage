package mage.cards.d;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DomrisAmbush extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public DomrisAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Put a +1/+1 counter on target creature you control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Then that creature deals damage equal to its power to target creature or planeswalker you don't control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("that creature").concatBy("Then"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private DomrisAmbush(final DomrisAmbush card) {
        super(card);
    }

    @Override
    public DomrisAmbush copy() {
        return new DomrisAmbush(this);
    }
}
