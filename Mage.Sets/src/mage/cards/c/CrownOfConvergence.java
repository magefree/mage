
package mage.cards.c;

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
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CrownOfConvergence extends CardImpl {

    private static final String rule1 = "As long as the top card of your library is a creature card, creatures you control that share a color with that card get +1/+1";

    public CrownOfConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));

        // As long as the top card of your library is a creature card, creatures you control that share a color with that card get +1/+1.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new CrownOfConvergenceColorBoostEffect(), new TopLibraryCardTypeCondition(CardType.CREATURE), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {G}{W}: Put the top card of your library on the bottom of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrownOfConvergenceEffect(), new ManaCostsImpl<>("{G}{W}")));
    }

    private CrownOfConvergence(final CrownOfConvergence card) {
        super(card);
    }

    @Override
    public CrownOfConvergence copy() {
        return new CrownOfConvergence(this);
    }
}

class CrownOfConvergenceColorBoostEffect extends BoostAllEffect {

    private static final String effectText = "creatures you control that share a color with that card get +1/+1";

    CrownOfConvergenceColorBoostEffect() {
        super(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        staticText = effectText;
    }

    private CrownOfConvergenceColorBoostEffect(CrownOfConvergenceColorBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Card topCard = you.getLibrary().getFromTop(game);
            if (topCard != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game)) {
                    if (permanent.getColor(game).shares(topCard.getColor(game)) && !permanent.getColor(game).isColorless()) {
                        permanent.addPower(power.calculate(game, source, this));
                        permanent.addToughness(toughness.calculate(game, source, this));
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CrownOfConvergenceColorBoostEffect copy() {
        return new CrownOfConvergenceColorBoostEffect(this);
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
