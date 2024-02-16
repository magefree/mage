package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.WeirdToken2;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExperimentalOverload extends CardImpl {

    public ExperimentalOverload(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{R}");

        // Create an X/X blue and red Weird creature token, where X is the number of instant and sorcery cards in your graveyard. Then you may return an instant or sorcery card from your graveyard to your hand. Exile Experimental Overload.
        this.getSpellAbility().addEffect(new ExperimentalOverloadEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private ExperimentalOverload(final ExperimentalOverload card) {
        super(card);
    }

    @Override
    public ExperimentalOverload copy() {
        return new ExperimentalOverload(this);
    }
}

class ExperimentalOverloadEffect extends OneShotEffect {

    ExperimentalOverloadEffect() {
        super(Outcome.Benefit);
        staticText = "Create an X/X blue and red Weird creature token, " +
                "where X is the number of instant and sorcery cards in your graveyard. " +
                "Then you may return an instant or sorcery card from your graveyard to your hand";
    }

    private ExperimentalOverloadEffect(final ExperimentalOverloadEffect effect) {
        super(effect);
    }

    @Override
    public ExperimentalOverloadEffect copy() {
        return new ExperimentalOverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int spellCount = player.getGraveyard().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game);
        new WeirdToken2(spellCount).putOntoBattlefield(1, game, source, source.getControllerId());
        if (spellCount < 1) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, true
        );
        player.choose(outcome, player.getGraveyard(), target, source, game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
