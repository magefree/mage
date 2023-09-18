
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Backfir3
 */
public final class RuneOfProtectionBlack extends CardImpl {

    private static final FilterObject filter = new FilterObject("black source");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RuneOfProtectionBlack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // {W}: The next time a black source of your choice would deal damage to you this turn, prevent that damage.
        Effect effect = new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn, filter);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{W}")));
        // Cycling {2} ({2}, Discard this card: Draw a card.)
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RuneOfProtectionBlack(final RuneOfProtectionBlack card) {
        super(card);
    }

    @Override
    public RuneOfProtectionBlack copy() {
        return new RuneOfProtectionBlack(this);
    }
}
