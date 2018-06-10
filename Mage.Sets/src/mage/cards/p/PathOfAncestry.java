package mage.cards.p;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class PathOfAncestry extends CardImpl {

    public PathOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Path of Ancestry enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // T: Add one mana of any color in your commander's color identity.
        Ability ability = new CommanderColorIdentityManaAbility();
        this.addAbility(ability);

        // When that mana is spent to cast a creature spell that shares a creature type with your commander, scry 1.
        this.addAbility(new PathOfAncestryTriggeredAbility(new ScryEffect(1)));
    }

    public PathOfAncestry(final PathOfAncestry card) {
        super(card);
    }

    @Override
    public PathOfAncestry copy() {
        return new PathOfAncestry(this);
    }
}

class PathOfAncestryTriggeredAbility extends TriggeredAbilityImpl {

    public PathOfAncestryTriggeredAbility(Effect effect) {
        super(Zone.ALL, effect, true);
    }

    public PathOfAncestryTriggeredAbility(final PathOfAncestryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PathOfAncestryTriggeredAbility copy() {
        return new PathOfAncestryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getSourceId())) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
            if (sourcePermanent != null) {
                boolean found = false;
                for (Ability ability : sourcePermanent.getAbilities()) {
                    if (ability instanceof CommanderColorIdentityManaAbility && ability.getOriginalId().toString().equals(event.getData())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null && spell.isCreature()) {
                        Player controller = game.getPlayer(getControllerId());
                        if (controller != null && controller.getCommandersIds() != null && !controller.getCommandersIds().isEmpty()) {
                            if (spell.getAbilities().contains(ChangelingAbility.getInstance())) {
                                for (UUID cmdr : controller.getCommandersIds()) {
                                    MageObject commander = game.getObject(cmdr);
                                    if (commander != null) {
                                        if (commander.getAbilities().contains(ChangelingAbility.getInstance())) {
                                            return true;
                                        }
                                        Iterator<SubType> cmdrSubs = commander.getSubtype(game).iterator();
                                        while (cmdrSubs.hasNext()) {
                                            SubType sType = cmdrSubs.next();
                                            if (sType.getSubTypeSet() == SubTypeSet.CreatureType) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                            Iterator<SubType> spellSubs = spell.getSubtype(game).iterator();
                            while (spellSubs.hasNext()) {
                                SubType sType = spellSubs.next();
                                if (sType.getSubTypeSet() == SubTypeSet.CreatureType) {
                                    for (UUID cmdr : controller.getCommandersIds()) {
                                        MageObject commander = game.getObject(cmdr);
                                        if (commander != null && (commander.hasSubtype(sType, game) || commander.getAbilities().contains(ChangelingAbility.getInstance()))) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When that mana is used to cast a creature spell that shares a creature type with your commander, " + super.getRule();
    }
}
