
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class GoldnightCastigator extends CardImpl {

    public GoldnightCastigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If a source would deal damage to you, it deals double that damage to you instead.
        // If a source would deal damage to Goldnight Castigator, it deals double that damage to Goldkight Castigator instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoldnightCastigatorDoubleDamageEffect()));
    }

    private GoldnightCastigator(final GoldnightCastigator card) {
        super(card);
    }

    @Override
    public GoldnightCastigator copy() {
        return new GoldnightCastigator(this);
    }
}

class GoldnightCastigatorDoubleDamageEffect extends ReplacementEffectImpl {

    public GoldnightCastigatorDoubleDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to you, it deals double that damage to you instead."
            + "<br>If a source would deal damage to {this}, it deals double that damage to {this} instead.";
    }

    private GoldnightCastigatorDoubleDamageEffect(final GoldnightCastigatorDoubleDamageEffect effect) {
        super(effect);
    }

    @Override
    public GoldnightCastigatorDoubleDamageEffect copy() {
        return new GoldnightCastigatorDoubleDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                if (event.getTargetId().equals(source.getControllerId())) {
                    event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
                }
                break;
            case DAMAGE_PERMANENT:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null) {
                    if (permanent.getId().equals(source.getSourceId())) {
                        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
                    }
                }
        }
        return false;
    }
}
