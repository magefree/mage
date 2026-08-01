package mage.cards.c;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ConsiderThePrimeDirective extends CardImpl {

    public ConsiderThePrimeDirective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one --
        // * Interfere -- Counter target spell unless its controller pays {3}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().withFlavorWord("Interfere");

        // * Observe -- Tap up to one target creature and put a stun counter on it. Draw a card.
        Mode mode = new Mode(new TapTargetEffect()).withFlavorWord("Observe");
        mode.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
            .withTargetDescription("it").concatBy("and")
        );
        mode.addEffect(new DrawCardSourceControllerEffect(1));
        mode.addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addMode(mode);
    }

    private ConsiderThePrimeDirective(final ConsiderThePrimeDirective card) {
        super(card);
    }

    @Override
    public ConsiderThePrimeDirective copy() {
        return new ConsiderThePrimeDirective(this);
    }
}
