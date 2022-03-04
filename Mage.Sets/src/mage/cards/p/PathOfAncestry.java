package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfAncestry extends CardImpl {

    public PathOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Path of Ancestry enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any color in your commander's color identity. When that mana is spent to cast a creature spell that shares a creature type with your commander, scry 1.
        Ability ability = new CommanderColorIdentityManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new PathOfAncestryTriggeredAbility()));
        this.addAbility(ability);
    }

    private PathOfAncestry(final PathOfAncestry card) {
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
        if (sourcePermanent == null
                || sourcePermanent
                .getAbilities(game)
                .stream()
                .filter(CommanderColorIdentityManaAbility.class::isInstance)
                .map(Ability::getOriginalId)
                .map(UUID::toString)
                .noneMatch(event.getData()::equals)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        Player player = game.getPlayer(getControllerId());
        if (player == null) {
            return false;
        }

        // share creature type with commander
        for (Card commander : game.getCommanderCardsFromAnyZones(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, Zone.ALL)) {
            if (spell.getCard().shareCreatureTypes(game, commander)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInactive(Game game) {
        if (super.isInactive(game)) {
            return true;
        }

        // must remove effect on empty mana pool to fix accumulate bug
        Player player = game.getPlayer(this.getControllerId());
        if (player == null) {
            return true;
        }

        // if no mana in pool then it can be discarded
        return player.getManaPool().getManaItems().stream().noneMatch(m -> m.getSourceId().equals(getSourceId()));
    }

    @Override
    public String getRule() {
        return "When that mana is spent to cast a creature spell that shares a creature type with your commander, " +
                "scry 1. " +
                "<i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>";
    }
}
