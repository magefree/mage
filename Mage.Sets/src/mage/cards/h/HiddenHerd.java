package mage.cards.h;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HiddenHerd extends CardImpl {

    public HiddenHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent plays a nonbasic land, if Hidden Herd is an enchantment, Hidden Herd becomes a 3/3 Beast creature.
        this.addAbility(new HiddenHerdAbility());
    }

    private HiddenHerd(final HiddenHerd card) {
        super(card);
    }

    @Override
    public HiddenHerd copy() {
        return new HiddenHerd(this);
    }
}

class HiddenHerdAbility extends TriggeredAbilityImpl {

    public HiddenHerdAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 Beast creature", SubType.BEAST),
                null, Duration.WhileOnBattlefield
        ), false);
        this.withInterveningIf(SourceIsEnchantmentCondition.instance);
        this.withRuleTextReplacement(true);
        this.setTriggerPhrase("When an opponent plays a nonbasic land, ");
    }

    private HiddenHerdAbility(final HiddenHerdAbility ability) {
        super(ability);
    }

    @Override
    public HiddenHerdAbility copy() {
        return new HiddenHerdAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return land != null && game.getOpponents(getControllerId()).contains(event.getPlayerId()) && !land.isBasic(game);
    }
}
