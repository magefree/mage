package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class Browbeat extends CardImpl {

    public Browbeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Any player may have Browbeat deal 5 damage to them. If no one does, target player draws three cards.
        this.getSpellAbility().addEffect(new BrowbeatDrawEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Browbeat(final Browbeat card) {
        super(card);
    }

    @Override
    public Browbeat copy() {
        return new Browbeat(this);
    }
}

class BrowbeatDrawEffect extends OneShotEffect {

    public BrowbeatDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "Any player may have {this} deal 5 damage to them. If no one does, target player draws three cards.";
    }

    private BrowbeatDrawEffect(final BrowbeatDrawEffect effect) {
        super(effect);
    }

    @Override
    public BrowbeatDrawEffect copy() {
        return new BrowbeatDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        StackObject spell = null;
        for (StackObject object : game.getStack()) {
            if (object instanceof Spell && object.getSourceId().equals(source.getSourceId())) {
                spell = object;
            }
        }
        if (spell != null) {
            boolean drawCards = true;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.chooseUse(Outcome.Detriment, "Have " + spell.getLogName() + " deal 5 damage to you?", source, game)) {
                    drawCards = false;
                    player.damage(5, source.getSourceId(), source, game);
                    game.informPlayers(player.getLogName() + " has " + spell.getLogName() + " deal 5 to them");
                }
            }
            if (drawCards) {
                UUID targetPlayer = getTargetPointer().getFirst(game, source);
                if (targetPlayer != null) {
                    Player player = game.getPlayer(targetPlayer);
                    if (player != null) {
                        player.drawCards(3, source, game);
                    }
                }
            }
            return drawCards;
        }
        return false;
    }

}
