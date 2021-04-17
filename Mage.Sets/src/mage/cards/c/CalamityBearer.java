package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class CalamityBearer extends CardImpl {

    public CalamityBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a Giant source you control would deal damage to a permanent or player,
        // it deals double that damage to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new CalamityBearerEffect()));
    }

    private CalamityBearer(final CalamityBearer card) {
        super(card);
    }

    @Override
    public CalamityBearer copy() {
        return new CalamityBearer(this);
    }
}

class CalamityBearerEffect extends ReplacementEffectImpl {

    CalamityBearerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "If a Giant source you control would deal damage to a permanent or player, " +
                "it deals double that damage to that permanent or player instead";
    }

    private CalamityBearerEffect(final CalamityBearerEffect effect) {
        super(effect);
    }

    @Override
    public CalamityBearerEffect copy() {
        return new CalamityBearerEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
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
        if (event.getAmount() > 0 && source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            MageObject sourceObject;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (sourcePermanent == null) {
                sourceObject = game.getObject(event.getSourceId());
            } else {
                sourceObject = sourcePermanent;
            }
            return sourceObject != null && sourceObject.hasSubtype(SubType.GIANT, game);
        }
        return false;
    }
}
