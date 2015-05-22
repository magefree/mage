package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 * 702.33. Madness
 *
 * 702.33a. Madness is a keyword that represents two abilities.
 *
 * The first is a static ability that functions while the card with madness is in a player's hand.
 * The second is a triggered ability that functions when the first ability is applied.
 *
 * "Madness [cost]" means "If a player would discard this card, that player discards it, but may exile it instead of putting it into his or her graveyard" and
 * "When this card is exiled this way, its owner may cast it by paying [cost] rather than paying its mana cost.
 * If that player doesn't, he or she puts this card into his or her graveyard.
 *
 * 702.33b. Casting a spell using its madness ability follows the rules for paying alternative costs in rules 601.2b and 601.2e-g.
 *
 * @author LevelX2
 */

public class MadnessAbility extends StaticAbility {

    private String rule;
    
    @SuppressWarnings("unchecked")
    public MadnessAbility(Card card, ManaCosts madnessCost) {
        super(Zone.HAND, new MadnessReplacementEffect((ManaCosts<ManaCost>)madnessCost));
        addSubAbility(new MadnessTriggeredAbility((ManaCosts<ManaCost>)madnessCost));
        rule = "Madness " + madnessCost.getText() + " <i>(If you discard this card, you may cast it for its madness cost instead of putting it into your graveyard.)<i/>";        
    }

    public MadnessAbility(final MadnessAbility ability) {
        super(ability);
    }

    @Override
    public MadnessAbility copy() {
        return new MadnessAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }
}


class MadnessReplacementEffect extends ReplacementEffectImpl {
    
    public MadnessReplacementEffect(ManaCosts<ManaCost> madnessCost) {
        super(Duration.EndOfGame, Outcome.Benefit);        
        staticText = "Madness " + madnessCost.getText() + " <i>(If you discard this card, you may cast it for its madness cost instead of putting it into your graveyard.)<i/>";
    }

    public MadnessReplacementEffect(final MadnessReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MadnessReplacementEffect copy() {
        return new MadnessReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                if (controller.chooseUse(outcome, "Move " + card.getLogName() + " to exile to cast it by Madness?", game)) {
                    controller.moveCardToExileWithInfo(card, source.getSourceId(), "Madness", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.MADNESS_CARD_EXILED, card.getId(), card.getId(),controller.getId()));
                    return true;
                }
            }
        }
        return false;
    }
        
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) &&
                ((ZoneChangeEvent) event).getFromZone() == Zone.HAND && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD;
    }

}

/**
 * Checks for the MADNESS_CARD_EXILED event to ask the player
 * if he wants to cast the card by it's Madness costs.
 * If not, the card goes to the graveyard.
 */
class MadnessTriggeredAbility extends TriggeredAbilityImpl {
    
    MadnessTriggeredAbility(ManaCosts<ManaCost> madnessCost ) {
        super(Zone.EXILED, new MadnessCastEffect(madnessCost), true);
        this.setRuleVisible(false);        
    }

    MadnessTriggeredAbility(final MadnessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MadnessTriggeredAbility copy() {
        return new MadnessTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MADNESS_CARD_EXILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public boolean resolve(Game game) {
        if (!super.resolve(game)) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                Player  owner = game.getPlayer(card.getOwnerId());
                if (owner != null) {
                    // if cast was not successfull, the card is moved to graveyard
                    owner.moveCards(card, Zone.EXILED, Zone.GRAVEYARD, this, game);
                }
            }              
            return false;
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When this card is exiled this way, " + super.getRule();
    }
}

class MadnessCastEffect extends OneShotEffect {
    
    private final ManaCosts<ManaCost> madnessCost;
    
    public MadnessCastEffect(ManaCosts<ManaCost> madnessCost) {
        super(Outcome.Benefit);
        this.madnessCost = madnessCost;
        staticText = "cast it by paying " + madnessCost.getText()  + " rather than paying its mana cost. If that player doesn’t, he or she puts this card into his or her graveyard.";
    }

    public MadnessCastEffect(final MadnessCastEffect effect) {
        super(effect);
        this.madnessCost = effect.madnessCost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player owner = null;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            owner = game.getPlayer(card.getOwnerId());
        }        
        if (owner != null && card != null) {
            ManaCosts<ManaCost> costRef = card.getSpellAbility().getManaCostsToPay();
            // replace with the new cost
            costRef.clear();
            costRef.add(madnessCost);
            boolean result = owner.cast(card.getSpellAbility(), game, false);
            // Reset the casting costs (in case the player cancels cast and plays the card later)
            // TODO: CHeck if this is neccessary
            costRef.clear();
            for (ManaCost manaCost : card.getSpellAbility().getManaCosts()) {
                costRef.add(manaCost);
            }
            return result;

        }
        return false;
    }

    @Override
    public MadnessCastEffect copy() {
        return new MadnessCastEffect(this);
    }
}
