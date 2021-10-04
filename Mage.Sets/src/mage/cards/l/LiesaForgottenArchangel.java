package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class LiesaForgottenArchangel extends CardImpl {

    private final static FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public LiesaForgottenArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another nontoken creature you control dies, return that card to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesCreatureTriggeredAbility(new LiesaForgottenArchangelReturnToHandEffect(), false, filter, true));

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new LiesaForgottenArchangelReplacementEffect()));
    }

    private LiesaForgottenArchangel(final LiesaForgottenArchangel card) {
        super(card);
    }

    @Override
    public LiesaForgottenArchangel copy() {
        return new LiesaForgottenArchangel(this);
    }
}

class LiesaForgottenArchangelReturnToHandEffect extends OneShotEffect {

    public LiesaForgottenArchangelReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return that card to its owner's hand at the beginning of the next end step";
    }

    private LiesaForgottenArchangelReturnToHandEffect(final LiesaForgottenArchangelReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public LiesaForgottenArchangelReturnToHandEffect copy() {
        return new LiesaForgottenArchangelReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return that card to its owner's hand");
        effect.setTargetPointer(targetPointer);
        DelayedTriggeredAbility ability = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }
}

class LiesaForgottenArchangelReplacementEffect extends ReplacementEffectImpl {

    public LiesaForgottenArchangelReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a creature an opponent controls would die, exile it instead";
    }

    private LiesaForgottenArchangelReplacementEffect(final LiesaForgottenArchangelReplacementEffect effect) {
        super(effect);
    }

    @Override
    public LiesaForgottenArchangelReplacementEffect copy() {
        return new LiesaForgottenArchangelReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null && permanent.isCreature()) {
                Player player = game.getPlayer(source.getControllerId());
                return player != null && player.hasOpponent(permanent.getControllerId(), game);
            }
        }
        return false;
    }
}
