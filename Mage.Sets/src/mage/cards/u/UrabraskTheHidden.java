
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class UrabraskTheHidden extends CardImpl {

    public UrabraskTheHidden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PRAETOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures"))));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UrabraskTheHiddenEffect()));
    }

    public UrabraskTheHidden(final UrabraskTheHidden card) {
        super(card);
    }

    @Override
    public UrabraskTheHidden copy() {
        return new UrabraskTheHidden(this);
    }
}

class UrabraskTheHiddenEffect extends ReplacementEffectImpl {

    UrabraskTheHiddenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures your opponents control enter the battlefield tapped";
    }

    UrabraskTheHiddenEffect(final UrabraskTheHiddenEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UrabraskTheHiddenEffect copy() {
        return new UrabraskTheHiddenEffect(this);
    }
}
