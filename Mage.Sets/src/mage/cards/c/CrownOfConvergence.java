
package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TopLibraryCardTypeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CrownOfConvergence extends CardImpl {

    private static final String rule1 = "As long as the top card of your library is a creature card, creatures you control that share a color with that card get +1/+1";

    private static final FilterPermanent shareColorFilter = new FilterControlledCreaturePermanent("creatures you control that share a color with that card");

    static {
        shareColorFilter.add(ShareColorTopCardPredicate.instance);
    }

    public CrownOfConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithTheTopCardRevealedEffect()));

        // As long as the top card of your library is a creature card, creatures you control that share a color with that card get +1/+1.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, shareColorFilter), new TopLibraryCardTypeCondition(CardType.CREATURE), rule1);
        this.addAbility(new SimpleStaticAbility(effect));

        // {G}{W}: Put the top card of your library on the bottom of your library.
        this.addAbility(new SimpleActivatedAbility(new CrownOfConvergenceEffect(), new ManaCostsImpl<>("{G}{W}")));
    }

    private CrownOfConvergence(final CrownOfConvergence card) {
        super(card);
    }

    @Override
    public CrownOfConvergence copy() {
        return new CrownOfConvergence(this);
    }
}

class CrownOfConvergenceEffect extends OneShotEffect {

    CrownOfConvergenceEffect() {
        super(Outcome.Neutral);
        staticText = "Put the top card of your library on the bottom of your library";
    }

    private CrownOfConvergenceEffect(final CrownOfConvergenceEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfConvergenceEffect copy() {
        return new CrownOfConvergenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.putCardsOnBottomOfLibrary(new CardsImpl(card), game, source, true);
            }
            return true;
        }
        return false;
    }

}

enum ShareColorTopCardPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        Player you = game.getPlayer(input.getSource().getControllerId());
        if (you == null) {
            return false;
        }
        Card topCard = you.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        return input.getObject().getColor(game).shares(topCard.getColor(game));
    }
}
