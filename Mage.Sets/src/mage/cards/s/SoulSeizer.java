package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class SoulSeizer extends CardImpl {

    public SoulSeizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.secondSideCardClazz = mage.cards.g.GhastlyHaunting.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // When Soul Seizer deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls.
        this.addAbility(new TransformAbility());
        this.addAbility(new SoulSeizerTriggeredAbility());
    }

    private SoulSeizer(final SoulSeizer card) {
        super(card);
    }

    @Override
    public SoulSeizer copy() {
        return new SoulSeizer(this);
    }
}

class SoulSeizerTriggeredAbility extends TriggeredAbilityImpl {

    public SoulSeizerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SoulSeizerEffect(), true);
    }

    public SoulSeizerTriggeredAbility(SoulSeizerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoulSeizerTriggeredAbility copy() {
        return new SoulSeizerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
                filter.add(new ControllerIdPredicate(opponent.getId()));

                this.getTargets().clear();
                this.addTarget(new TargetCreaturePermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls";
    }
}

class SoulSeizerEffect extends OneShotEffect {

    public SoulSeizerEffect() {
        super(Outcome.GainControl);
    }

    public SoulSeizerEffect(final SoulSeizerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || !permanent.transform(source, game)) {
            return false;
        }
        Permanent attachTo = game.getPermanent(targetPointer.getFirst(game, source));
        return attachTo != null && attachTo.addAttachment(source.getSourceId(), source, game);
    }

    @Override
    public SoulSeizerEffect copy() {
        return new SoulSeizerEffect(this);
    }

}