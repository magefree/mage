package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeiferBalambRival extends CardImpl {

    public SeiferBalambRival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you attack a player, goad target creature that player controls.
        Ability ability = new AttacksPlayerWithCreaturesTriggeredAbility(
                new GoadTargetEffect().setText("goad target creature that player controls"), SetTargetPointer.NONE
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        // Whenever a creature attacking one of your opponents becomes blocked by two or more creatures, that attacking creature gains deathtouch until end of turn.
        this.addAbility(new SeiferBalambRivalTriggeredAbility());
    }

    private SeiferBalambRival(final SeiferBalambRival card) {
        super(card);
    }

    @Override
    public SeiferBalambRival copy() {
        return new SeiferBalambRival(this);
    }
}

class SeiferBalambRivalTriggeredAbility extends TriggeredAbilityImpl {

    SeiferBalambRivalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DeathtouchAbility.getInstance()).setText("that attacking creature gains deathtouch until end of turn"));
        this.setTriggerPhrase("Whenever a creature attacking one of your opponents becomes blocked by two or more creatures, ");
    }

    private SeiferBalambRivalTriggeredAbility(final SeiferBalambRivalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SeiferBalambRivalTriggeredAbility copy() {
        return new SeiferBalambRivalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game
                .getOpponents(game.getCombat().getDefenderId(event.getTargetId()))
                .contains(this.getControllerId())
                || game
                .getCombat()
                .findGroup(event.getTargetId())
                .getBlockers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game))
                .count() < 2) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}
