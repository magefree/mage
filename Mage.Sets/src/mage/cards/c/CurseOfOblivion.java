
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfOblivion extends CardImpl {

    public CurseOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer target = new TargetPlayer();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(target.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player exiles two cards from their graveyard.
        this.addAbility(new CurseOfOblivionAbility());
    }

    public CurseOfOblivion(final CurseOfOblivion card) {
        super(card);
    }

    @Override
    public CurseOfOblivion copy() {
        return new CurseOfOblivion(this);
    }
}

class CurseOfOblivionAbility extends TriggeredAbilityImpl {

    public CurseOfOblivionAbility() {
        super(Zone.BATTLEFIELD, new ExileFromZoneTargetEffect(Zone.GRAVEYARD, null, "", new FilterCard(), 2));
    }

    public CurseOfOblivionAbility(final CurseOfOblivionAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfOblivionAbility copy() {
        return new CurseOfOblivionAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null && game.isActivePlayer(player.getId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, that player exiles two cards from their graveyard.";
    }

}
