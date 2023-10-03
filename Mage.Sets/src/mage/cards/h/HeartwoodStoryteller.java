
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class HeartwoodStoryteller extends CardImpl {

    public HeartwoodStoryteller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a player casts a noncreature spell, each of that player's opponents may draw a card.
        this.addAbility(new HeartwoodStorytellerTriggeredAbility());
    }

    private HeartwoodStoryteller(final HeartwoodStoryteller card) {
        super(card);
    }

    @Override
    public HeartwoodStoryteller copy() {
        return new HeartwoodStoryteller(this);
    }
}

class HeartwoodStorytellerTriggeredAbility extends TriggeredAbilityImpl {

    HeartwoodStorytellerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HeartwoodStorytellerEffect(), false);
    }

    private HeartwoodStorytellerTriggeredAbility(final HeartwoodStorytellerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeartwoodStorytellerTriggeredAbility copy() {
        return new HeartwoodStorytellerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && !spell.isCreature(game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a noncreature spell, each of that player's opponents may draw a card.";
    }
}

class HeartwoodStorytellerEffect extends OneShotEffect {

    HeartwoodStorytellerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each of that player's opponents may draw a card";
    }

    private HeartwoodStorytellerEffect(final HeartwoodStorytellerEffect effect) {
        super(effect);
    }

    @Override
    public HeartwoodStorytellerEffect copy() {
        return new HeartwoodStorytellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(this.getTargetPointer().getFirst(game, source))) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(outcome, "Draw a card?", source, game)) {
                    player.drawCards(1, source, game);
                }
            }
        }
        return true;
    }
}
