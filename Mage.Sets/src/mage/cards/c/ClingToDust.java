package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClingToDust extends CardImpl {

    public ClingToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Exile target card from a graveyard. If it was a creature card, you gain 3 life. Otherwise, you draw a card.
        this.getSpellAbility().addEffect(new ClingToDustEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());

        // Escape—{3}{B}, Exile five other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{B}", 5));
    }

    private ClingToDust(final ClingToDust card) {
        super(card);
    }

    @Override
    public ClingToDust copy() {
        return new ClingToDust(this);
    }
}

class ClingToDustEffect extends OneShotEffect {

    ClingToDustEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card from a graveyard. If it was a creature card, " +
                "you gain 3 life. Otherwise, you draw a card";
    }

    private ClingToDustEffect(final ClingToDustEffect effect) {
        super(effect);
    }

    @Override
    public ClingToDustEffect copy() {
        return new ClingToDustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        boolean isCreature = card.isCreature(game);
        if (!player.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        if (isCreature) {
            player.gainLife(3, game, source);
        } else {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
