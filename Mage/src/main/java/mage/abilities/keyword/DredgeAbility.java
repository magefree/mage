package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * If you would draw a card, you may mill X cards instead. If you do, return
 * this card from your graveyard to your hand.
 *
 * @author North
 */
public class DredgeAbility extends SimpleStaticAbility {

    public DredgeAbility(int value) {
        super(Zone.GRAVEYARD, new DredgeEffect(value));
    }

    protected DredgeAbility(final DredgeAbility ability) {
        super(ability);
    }

    @Override
    public DredgeAbility copy() {
        return new DredgeAbility(this);
    }
}

class DredgeEffect extends ReplacementEffectImpl {

    private final int amount;

    public DredgeEffect(int value) {
        super(Duration.WhileInGraveyard, Outcome.AIDontUseIt);
        this.amount = value;
        this.staticText = "Dredge " + value + " <i>(If you would draw a card, you may mill "
                + CardUtil.numberToText(value, "a") + (value > 1 ? " cards" : " card")
                + " instead. If you do, return this card from your graveyard to your hand.)</i>";
    }

    protected DredgeEffect(final DredgeEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DredgeEffect copy() {
        return new DredgeEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Player owner = game.getPlayer(game.getCard(source.getSourceId()).getOwnerId());
        if (owner != null
                && owner.getLibrary().size() >= amount
                && owner.chooseUse(outcome, new StringBuilder("Dredge ").append(sourceCard.getLogName()).
                append("? (").append(amount).append(" cards are milled)").toString(), source, game)) {
            if (!game.isSimulation()) {
                game.informPlayers(new StringBuilder(owner.getLogName()).append(" dredges ").append(sourceCard.getLogName()).toString());
            }
            owner.millCards(amount, source, game);
            owner.moveCards(sourceCard, Zone.HAND, source, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player owner = game.getPlayer(game.getCard(source.getSourceId()).getOwnerId());
        return (owner != null
                && event.getPlayerId().equals(owner.getId())
                && owner.getLibrary().size() >= amount);
    }
}
