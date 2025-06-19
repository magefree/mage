
package mage.cards.s;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.ChosenColorPredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */

public final class StoryCircle extends CardImpl {

    private static FilterSource filter = new FilterSource("a source of your choice of the chosen color");

    static {
        filter.add(ChosenColorPredicate.TRUE);
    }

    public StoryCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // As Story Circle enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // {W}: The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true, filter),
                new ManaCostsImpl<>("{W}")
        ));
    }

    private StoryCircle(final StoryCircle card) {
        super(card);
    }

    @Override
    public StoryCircle copy() {
        return new StoryCircle(this);
    }
}