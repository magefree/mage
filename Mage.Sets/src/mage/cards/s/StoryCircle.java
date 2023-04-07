
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
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
 * @author LoneFox
 */

public final class StoryCircle extends CardImpl {

    public StoryCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // As Story Circle enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // {W}: The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new StoryCircleEffect(), new ManaCostsImpl<>("{W}")));
    }

    private StoryCircle(final StoryCircle card) {
        super(card);
    }

    @Override
    public StoryCircle copy() {
        return new StoryCircle(this);
    }
}

class StoryCircleEffect extends PreventNextDamageFromChosenSourceToYouEffect {

    public StoryCircleEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.";
    }

    @Override
    public void init(Ability source, Game game) {
        FilterObject filter = targetSource.getFilter();
        filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(source.getSourceId() + "_color")));
        super.init(source, game);
    }

    public StoryCircleEffect(StoryCircleEffect effect) {
        super(effect);
    }

    @Override
    public StoryCircleEffect copy() {
        return new StoryCircleEffect(this);
    }

}
