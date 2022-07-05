
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class PrismaticCircle extends CardImpl {

    public PrismaticCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // As Prismatic Circle enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // {1}: The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PrismaticCircleEffect(), new ManaCostsImpl<>("{1}")));
    }

    private PrismaticCircle(final PrismaticCircle card) {
        super(card);
    }

    @Override
    public PrismaticCircle copy() {
        return new PrismaticCircle(this);
    }
}

class PrismaticCircleEffect extends PreventNextDamageFromChosenSourceToYouEffect {

    public PrismaticCircleEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.";
    }

    @Override
    public void init(Ability source, Game game) {
        FilterObject filter = targetSource.getFilter();
        filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(source.getSourceId() + "_color")));
        super.init(source, game);
    }

    public PrismaticCircleEffect(PrismaticCircleEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticCircleEffect copy() {
        return new PrismaticCircleEffect(this);
    }

}
