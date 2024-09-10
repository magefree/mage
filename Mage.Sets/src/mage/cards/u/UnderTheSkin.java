package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author alma555
 */
public final class UnderTheSkin extends CardImpl {

    public UnderTheSkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Manifest Dread
        this.getSpellAbility().addEffect(new ManifestDreadEffect());

        // You may return a permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new UnderTheSkinEffect());
    }
    private UnderTheSkin(final UnderTheSkin card) {
        super(card);
    }

    @Override
    public UnderTheSkin copy() {
        return new UnderTheSkin(this);
    }
}

class UnderTheSkinEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    UnderTheSkinEffect() {
        super(Outcome.Benefit);
        this.concatBy("<br>");
        staticText = "You may return a permanent card from your graveyard to your hand.";
    }

    private UnderTheSkinEffect(final UnderTheSkinEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.u.UnderTheSkinEffect copy() {
        return new UnderTheSkinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);

        player.choose(Outcome.ReturnToHand, target, source, game);

        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}