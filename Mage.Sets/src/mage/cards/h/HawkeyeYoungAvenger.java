package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author muz
 */
public final class HawkeyeYoungAvenger extends CardImpl {

    public HawkeyeYoungAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // If a source you control would deal noncombat damage to an opponent or a permanent an opponent controls,
        // instead it deals that much damage plus X, where X is Hawkeye's power.
        this.addAbility(new SimpleStaticAbility(new HawkeyeYoungAvengerEffect()));
    }

    private HawkeyeYoungAvenger(final HawkeyeYoungAvenger card) {
        super(card);
    }

    @Override
    public HawkeyeYoungAvenger copy() {
        return new HawkeyeYoungAvenger(this);
    }
}

class HawkeyeYoungAvengerEffect extends ReplacementEffectImpl {

    private static final DynamicValue xValue = SourcePermanentPowerValue.NOT_NEGATIVE;

    HawkeyeYoungAvengerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "if a source you control would deal noncombat damage to an opponent or "
            + "a permanent an opponent controls, instead it deals that much damage plus X, "
            + "where X is Hawkeye's power";
    }

    private HawkeyeYoungAvengerEffect(final HawkeyeYoungAvengerEffect effect) {
        super(effect);
    }

    @Override
    public HawkeyeYoungAvengerEffect copy() {
        return new HawkeyeYoungAvengerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((DamageEvent) event).isCombatDamage()
                || event.getAmount() < 1
                || !source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        return opponents.contains(event.getTargetId())
                || opponents.contains(game.getControllerId(event.getTargetId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), xValue.calculate(game, source, this)));
        return false;
    }
}
