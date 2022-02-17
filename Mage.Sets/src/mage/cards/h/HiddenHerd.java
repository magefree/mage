
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author TheElk801
 */
public final class HiddenHerd extends CardImpl {

    public HiddenHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent plays a nonbasic land, if Hidden Herd is an enchantment, Hidden Herd becomes a 3/3 Beast creature.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new HiddenHerdAbility(),
                new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent plays a nonbasic land, if {this} is an enchantment, {this} becomes a 3/3 Beast creature."
        ));
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
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new HiddenHerdBeast(), "", Duration.WhileOnBattlefield, true, false), false);
    }

    public HiddenHerdAbility(final HiddenHerdAbility ability) {
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
        return game.getOpponents(controllerId).contains(event.getPlayerId()) && !land.getSuperType().contains(SuperType.BASIC);
    }

    @Override
    public String getRule() {
        return "When an opponent plays a nonbasic land, if {this} is an enchantment, {this} becomes a 3/3 Beast creature.";
    }
}

class HiddenHerdBeast extends TokenImpl {

    public HiddenHerdBeast() {
        super("Beast", "3/3 Beast creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
    public HiddenHerdBeast(final HiddenHerdBeast token) {
        super(token);
    }

    public HiddenHerdBeast copy() {
        return new HiddenHerdBeast(this);
    }
}
