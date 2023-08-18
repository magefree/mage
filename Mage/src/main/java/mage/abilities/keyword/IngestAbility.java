package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class IngestAbility extends DealsCombatDamageToAPlayerTriggeredAbility {

    public IngestAbility() {
        super(new IngestEffect(), false, true);

    }

    public IngestAbility(IngestAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Ingest <i>(Whenever this creature deals combat damage to a player, that player exiles the top card of their library.)</i>";
    }

    @Override
    public IngestAbility copy() {
        return new IngestAbility(this);
    }
}

class IngestEffect extends OneShotEffect {

    public IngestEffect() {
        super(Outcome.Exile);
        this.staticText = "that player exiles the top card of their library";
    }

    protected IngestEffect(final IngestEffect effect) {
        super(effect);
    }

    @Override
    public IngestEffect copy() {
        return new IngestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if (card != null) {
                targetPlayer.moveCards(card, Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }
}
