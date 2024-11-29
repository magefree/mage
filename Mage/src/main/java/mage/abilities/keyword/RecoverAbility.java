package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * 702.58a Recover is a triggered ability that functions only while the card
 * with recover is in a player's graveyard. “Recover [cost]” means “When a
 * creature is put into your graveyard from the battlefield, you may pay [cost].
 * If you do, return this card from your graveyard to your hand. Otherwise,
 * exile this card.”
 *
 * @author LevelX2
 */
public class RecoverAbility extends TriggeredAbilityImpl {

    public RecoverAbility(Cost cost, Card card) {
        super(Zone.GRAVEYARD, new RecoverEffect(cost, card), false);
        setLeavesTheBattlefieldTrigger(true);
    }

    protected RecoverAbility(final RecoverAbility ability) {
        super(ability);
    }

    @Override
    public RecoverAbility copy() {
        return new RecoverAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            if (zEvent.getTarget().isOwnedBy(getControllerId())
                    && zEvent.getTarget().isCreature(game)
                    && !zEvent.getTarget().getId().equals(getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return super.getRule();
    }
}

class RecoverEffect extends DoIfCostPaid {

    public RecoverEffect(Cost cost, Card card) {
        super(new ReturnToHandSourceEffect(), new ExileSourceEffect(), cost);
        this.staticText = setText(cost, card.isCreature());
    }

    protected RecoverEffect(final RecoverEffect effect) {
        super(effect);
    }

    @Override
    public RecoverEffect copy() {
        return new RecoverEffect(this);
    }

    private String setText(Cost cost, boolean creature) {
        StringBuilder sb = new StringBuilder();
        sb.append("Recover");
        if (cost instanceof ManaCost) {
            sb.append(' ').append(cost.getText()).append(' ');
        } else {
            sb.append("&mdash;").append(cost.getText()).append(". ");
        }
        sb.append("<i>(When ").append(creature ? "another" : "a").append(" creature is put into your graveyard from the battlefield, you may pay ");
        sb.append(cost.getText());
        sb.append(". If you do, return this card from your graveyard to your hand. Otherwise, exile this card.)</i>");
        return sb.toString();
    }
}
