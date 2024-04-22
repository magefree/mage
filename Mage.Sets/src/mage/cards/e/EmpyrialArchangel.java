
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class EmpyrialArchangel extends CardImpl {

    public EmpyrialArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}{W}{U}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
        // All damage that would be dealt to you is dealt to Empyrial Archangel instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EmpyrialArchangelEffect()));
    }

    private EmpyrialArchangel(final EmpyrialArchangel card) {
        super(card);
    }

    @Override
    public EmpyrialArchangel copy() {
        return new EmpyrialArchangel(this);
    }
}

class EmpyrialArchangelEffect extends ReplacementEffectImpl {
    EmpyrialArchangelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to you is dealt to {this} instead";
    }

    private EmpyrialArchangelEffect(final EmpyrialArchangelEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public EmpyrialArchangelEffect copy() {
        return new EmpyrialArchangelEffect(this);
    }
}
