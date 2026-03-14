package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorRoomFracturedRealm extends RoomCard {

    public MirrorRoomFracturedRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{U}", "{5}{U}{U}");

        // Mirror Room
        // When you unlock this door, create a token that's a copy of target creature you control, except it's a Reflection in addition to its other creature types.
        Ability ability = new UnlockThisDoorTriggeredAbility(
                new CreateTokenCopyTargetEffect()
                        .withAdditionalSubType(SubType.REFLECTION)
                        .setText("create a token that's a copy of target creature you control, " +
                                "except it's a Reflection in addition to its other creature types"),
                false, true
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Fractured Realm
        // If a triggered ability of a permanent you control triggers, that ability triggers an additional time.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new FracturedRealmEffect()));
    }

    private MirrorRoomFracturedRealm(final MirrorRoomFracturedRealm card) {
        super(card);
    }

    @Override
    public MirrorRoomFracturedRealm copy() {
        return new MirrorRoomFracturedRealm(this);
    }
}

class FracturedRealmEffect extends ReplacementEffectImpl {

    FracturedRealmEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of a permanent you control triggers, " +
                "that ability triggers an additional time";
    }

    private FracturedRealmEffect(final FracturedRealmEffect effect) {
        super(effect);
    }

    @Override
    public FracturedRealmEffect copy() {
        return new FracturedRealmEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
