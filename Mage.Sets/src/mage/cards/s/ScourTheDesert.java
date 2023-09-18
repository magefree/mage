package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BirdToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScourTheDesert extends CardImpl {

    public ScourTheDesert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Exile target creature card from your graveyard. Create X 1/1 white Bird creature tokens with flying, where X is the exiled card's toughness.
        this.getSpellAbility().addEffect(new ScourTheDesertEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private ScourTheDesert(final ScourTheDesert card) {
        super(card);
    }

    @Override
    public ScourTheDesert copy() {
        return new ScourTheDesert(this);
    }
}

class ScourTheDesertEffect extends OneShotEffect {

    ScourTheDesertEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from your graveyard. " +
                "Create X 1/1 white Bird creature tokens with flying, where X is the exiled card's toughness";
    }

    private ScourTheDesertEffect(final ScourTheDesertEffect effect) {
        super(effect);
    }

    @Override
    public ScourTheDesertEffect copy() {
        return new ScourTheDesertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        int toughness = card.getToughness().getValue();
        player.moveCards(card, Zone.EXILED, source, game);
        if (toughness > 0) {
            new BirdToken().putOntoBattlefield(toughness, game, source, source.getControllerId());
        }
        return true;
    }
}
