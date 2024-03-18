package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author DominionSpy
 */
public final class KarlovWatchdog extends CardImpl {

    public KarlovWatchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Permanents your opponents control can't be turned face up during your turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KarlovWatchdogEffect()));

        // Whenever you attack with three or more creatures, creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), 3));
    }

    private KarlovWatchdog(final KarlovWatchdog card) {
        super(card);
    }

    @Override
    public KarlovWatchdog copy() {
        return new KarlovWatchdog(this);
    }
}

class KarlovWatchdogEffect extends ContinuousRuleModifyingEffectImpl {

    KarlovWatchdogEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Permanents your opponents control can't be turned face up during your turn";
    }

    private KarlovWatchdogEffect(final KarlovWatchdogEffect effect) {
        super(effect);
    }

    @Override
    public KarlovWatchdogEffect copy() {
        return new KarlovWatchdogEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURN_FACE_UP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && game.isActivePlayer(source.getControllerId()) &&
                game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }
}
