package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfAncestry extends CardImpl {

    public PathOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Path of Ancestry enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // T: Add one mana of any color in your commander's color identity. When that mana is spent to cast a creature spell that shares a creature type with your commander, scry 1.
        Ability ability = new CommanderColorIdentityManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new PathOfAncestryTriggeredAbility()));
        this.addAbility(ability);
    }

    public PathOfAncestry(final PathOfAncestry card) {
        super(card);
    }

    @Override
    public PathOfAncestry copy() {
        return new PathOfAncestry(this);
    }
}

class PathOfAncestryTriggeredAbility extends DelayedTriggeredAbility {

    PathOfAncestryTriggeredAbility() {
        super(new ScryEffect(1), Duration.Custom, true, false);
    }

    private PathOfAncestryTriggeredAbility(final PathOfAncestryTriggeredAbility ability) {
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
        if (!getSourceId().equals(event.getSourceId())) {
            return false;
        }
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        boolean found = false;
        for (Ability ability : sourcePermanent.getAbilities()) {
            if (ability instanceof CommanderColorIdentityManaAbility
                    && ability.getOriginalId().toString().equals(event.getData())) {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || (spell.getSubtype(game).isEmpty()
                && !spell.hasAbility(ChangelingAbility.getInstance(), game))) {
            return false;
        }
        Player player = game.getPlayer(getControllerId());
        if (player == null) {
            return false;
        }
        boolean isAllA = false;
        Set<SubType> subTypeSet = new HashSet<>();
        for (UUID commanderId : game.getCommandersIds(player)) {
            Card commander = game.getPermanent(commanderId);
            if (commander == null) {
                commander = game.getCard(commanderId);
            }
            if (commander == null) {
                continue;
            }
            if (commander.isAllCreatureTypes()
                    || commander.hasAbility(ChangelingAbility.getInstance(), game)) {
                isAllA = true;
                break;
            }
            subTypeSet.addAll(commander.getSubtype(game));
        }
        subTypeSet.removeIf(subType -> subType.getSubTypeSet() != SubTypeSet.CreatureType);
        if (subTypeSet.isEmpty() && !isAllA) {
            return false;
        }
        return spell.hasAbility(ChangelingAbility.getInstance(), game)
                || spell.getSubtype(game).stream().anyMatch(subTypeSet::contains);
    }

    @Override
    public String getRule() {
        return "When that mana is spent to cast a creature spell that shares a creature type with your commander, scry 1.";
    }
}
