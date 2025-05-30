package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NerivHeartOfTheStorm extends CardImpl {

    public NerivHeartOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a creature you control that entered this turn would deal damage, it deals twice that much damage instead.
        this.addAbility(new SimpleStaticAbility(new NerivHeartOfTheStormEffect()));
    }

    private NerivHeartOfTheStorm(final NerivHeartOfTheStorm card) {
        super(card);
    }

    @Override
    public NerivHeartOfTheStorm copy() {
        return new NerivHeartOfTheStorm(this);
    }
}

class NerivHeartOfTheStormEffect extends ReplacementEffectImpl {

    NerivHeartOfTheStormEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "if a creature you control that entered this turn would deal damage, it deals twice that much damage instead";
    }

    private NerivHeartOfTheStormEffect(final NerivHeartOfTheStormEffect effect) {
        super(effect);
    }

    @Override
    public NerivHeartOfTheStormEffect copy() {
        return new NerivHeartOfTheStormEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return Optional
                .ofNullable(game.getPermanentOrLKIBattlefield(event.getSourceId()))
                .map(permanent -> permanent.isCreature(game)
                        && permanent.getTurnsOnBattlefield() == 0
                        && permanent.isControlledBy(source.getControllerId()))
                .orElse(false);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

}
