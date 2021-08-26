package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneEndeavor extends CardImpl {

    public ArcaneEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Roll two d8 and choose one result. Draw cards equal to that result. Then you may cast an instant or sorcery spell with mana value less than or equal to the other result from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new ArcaneEndeavorEffect());
    }

    private ArcaneEndeavor(final ArcaneEndeavor card) {
        super(card);
    }

    @Override
    public ArcaneEndeavor copy() {
        return new ArcaneEndeavor(this);
    }
}

class ArcaneEndeavorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value %mv or less from your hand"
    );

    ArcaneEndeavorEffect() {
        super(Outcome.Benefit);
        staticText = "roll two d8 and choose one result. Draw cards equal to that result. " +
                "Then you may cast an instant or sorcery spell with mana value less than " +
                "or equal to the other result from your hand without paying its mana cost";
    }

    private ArcaneEndeavorEffect(final ArcaneEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public ArcaneEndeavorEffect copy() {
        return new ArcaneEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Integer> results = player.rollDice(outcome, source, game, 8, 2, 0);
        int firstResult = results.get(0);
        int secondResult = results.get(1);
        int first, second;
        if (firstResult != secondResult && player.chooseUse(
                outcome, "Choose a number of cards to draw",
                "The other number will be the maximum mana value of the spell you cast",
                "" + firstResult, "" + secondResult, source, game
        )) {
            first = firstResult;
            second = secondResult;
        } else {
            first = secondResult;
            second = firstResult;
        }
        player.drawCards(first, source, game);
        new CastWithoutPayingManaCostEffect(StaticValue.get(second), filter).apply(game, source);
        return true;
    }
}
