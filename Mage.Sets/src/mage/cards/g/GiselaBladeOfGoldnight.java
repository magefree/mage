
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public final class GiselaBladeOfGoldnight extends CardImpl {

    public GiselaBladeOfGoldnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());

        // If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.
        // If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GiselaBladeOfGoldnightDoubleDamageEffect()));
    }

    public GiselaBladeOfGoldnight(final GiselaBladeOfGoldnight card) {
        super(card);
    }

    @Override
    public GiselaBladeOfGoldnight copy() {
        return new GiselaBladeOfGoldnight(this);
    }
}

class GiselaBladeOfGoldnightDoubleDamageEffect extends ReplacementEffectImpl {

    public GiselaBladeOfGoldnightDoubleDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead."
                + "If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up";
    }

    public GiselaBladeOfGoldnightDoubleDamageEffect(final GiselaBladeOfGoldnightDoubleDamageEffect effect) {
        super(effect);
    }

    @Override
    public GiselaBladeOfGoldnightDoubleDamageEffect copy() {
        return new GiselaBladeOfGoldnightDoubleDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE ||
                event.getType() == EventType.DAMAGE_PLANESWALKER ||
                event.getType() == EventType.DAMAGE_PLAYER;
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    private void preventDamage(GameEvent event, Ability source, UUID target, Game game) {
        int amount = (int) Math.ceil(event.getAmount() / 2.0);
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, target, source.getSourceId(), source.getControllerId(), amount, false);
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(event.getAmount() - amount);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getSourceId(), source.getControllerId(), amount));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                if (event.getTargetId().equals(source.getControllerId())) {
                    preventDamage(event, source, source.getControllerId(), game);
                } else if (game.getOpponents(source.getControllerId()).contains(event.getTargetId())) {
                    event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
                }
                break;
            case DAMAGE_CREATURE:
            case DAMAGE_PLANESWALKER:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null) {
                    if (permanent.isControlledBy(source.getControllerId())) {
                        preventDamage(event, source, permanent.getId(), game);
                    } else if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
                        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
                    }
                }
        }
        return false;
    }
}
