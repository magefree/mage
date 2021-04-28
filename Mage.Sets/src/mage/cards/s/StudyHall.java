package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StudyHall extends CardImpl {

    public StudyHall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add one mana of any color. When you spend this mana to cast your commander, scry X, where X is the number of times it's been cast from the command zone this game.
        AnyColorManaAbility ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new StudyHallTriggeredAbility()));
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private StudyHall(final StudyHall card) {
        super(card);
    }

    @Override
    public StudyHall copy() {
        return new StudyHall(this);
    }
}

class StudyHallTriggeredAbility extends DelayedTriggeredAbility {

    StudyHallTriggeredAbility() {
        super(null, Duration.Custom, true, false);
    }

    private StudyHallTriggeredAbility(final StudyHallTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StudyHallTriggeredAbility copy() {
        return new StudyHallTriggeredAbility(this);
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
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        if (sourcePermanent == null
                || sourcePermanent
                .getAbilities(game)
                .stream()
                .map(Ability::getOriginalId)
                .map(UUID::toString)
                .noneMatch(event.getData()::equals)) {
            return false;
        }
        Player player = game.getPlayer(getControllerId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (player == null
                || spell == null
                || watcher == null
                || !game.isCommanderObject(player, spell)) {
            return false;
        }
        getEffects().clear();
        addEffect(new ScryEffect(watcher.getPlaysCount(spell.getMainCard().getId())));
        return true;
    }

    @Override
    public boolean isInactive(Game game) {
        if (super.isInactive(game)) {
            return true;
        }
        // must remove effect on empty mana pool to fix accumulate bug
        // if no mana in pool then it can be discarded
        Player player = game.getPlayer(this.getControllerId());
        return player == null
                || player
                .getManaPool()
                .getManaItems()
                .stream()
                .map(ManaPoolItem::getSourceId)
                .noneMatch(getSourceId()::equals);
    }

    @Override
    public String getRule() {
        return "When you spend this mana to cast your commander, scry X, " +
                "where X is the number of times it's been cast from the command zone this game.";
    }
}
