package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MorbidBloom extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public MorbidBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{G}");

        // Exile target creature card from a graveyard, then create X 1/1 green Saproling creature tokens, where X is the exiled card's toughness.
        this.getSpellAbility().addEffect(new MorbidBloomEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private MorbidBloom(final MorbidBloom card) {
        super(card);
    }

    @Override
    public MorbidBloom copy() {
        return new MorbidBloom(this);
    }
}

class MorbidBloomEffect extends OneShotEffect {

    MorbidBloomEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target creature card from a graveyard, " +
                "then create X 1/1 green Saproling creature tokens, " +
                "where X is the exiled card's toughness";
    }

    private MorbidBloomEffect(final MorbidBloomEffect effect) {
        super(effect);
    }

    @Override
    public MorbidBloomEffect copy() {
        return new MorbidBloomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        int toughness = card.getToughness().getValue();
        if (toughness < 1) {
            return true;
        }
        return new SaprolingToken().putOntoBattlefield(toughness, game, source, player.getId());
    }
}
