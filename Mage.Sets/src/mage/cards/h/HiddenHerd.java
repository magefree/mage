package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
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
import mage.game.permanent.token.TokenImpl;

import java.util.Optional;
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
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new HiddenHerdBeast(), null, Duration.WhileOnBattlefield), false);
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
        return game.getOpponents(controllerId).contains(event.getPlayerId()) && !land.isBasic(game);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return Optional
                .ofNullable(getSourcePermanentIfItStillExists(game))
                .filter(permanent -> permanent.isEnchantment(game))
                .isPresent();
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

    private HiddenHerdBeast(final HiddenHerdBeast token) {
        super(token);
    }

    public HiddenHerdBeast copy() {
        return new HiddenHerdBeast(this);
    }
}
