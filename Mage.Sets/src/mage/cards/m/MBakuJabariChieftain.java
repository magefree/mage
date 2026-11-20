package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MonarchIsNotSetCondition;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class MBakuJabariChieftain extends CardImpl {

    public MBakuJabariChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if there is no monarch, target opponent becomes the monarch.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new BecomesMonarchTargetEffect())
                .withInterveningIf(MonarchIsNotSetCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(MonarchHint.instance));

        // Whenever a creature attacks one of your opponents, if that player is the monarch, that creature gets +1/+1 and gains trample until end of turn.
        this.addAbility(new MBakuJabariChieftainTriggeredAbility());
    }

    private MBakuJabariChieftain(final MBakuJabariChieftain card) {
        super(card);
    }

    @Override
    public MBakuJabariChieftain copy() {
        return new MBakuJabariChieftain(this);
    }
}

class MBakuJabariChieftainTriggeredAbility extends TriggeredAbilityImpl {

    MBakuJabariChieftainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1)
                .setText("that creature gets +1/+1"));
        this.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        this.withInterveningIf(MBakuJabariChieftainCondition.instance);
        this.setTriggerPhrase("Whenever a creature attacks one of your opponents, ");
        this.addHint(MonarchHint.instance);
    }

    private MBakuJabariChieftainTriggeredAbility(final MBakuJabariChieftainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MBakuJabariChieftainTriggeredAbility copy() {
        return new MBakuJabariChieftainTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
        getEffects().setValue("attackedPlayer", event.getTargetId());
        return true;
    }
}

enum MBakuJabariChieftainCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID attackedPlayerId = (UUID) source.getEffects().get(0).getValue("attackedPlayer");
        return attackedPlayerId != null && attackedPlayerId.equals(game.getMonarchId());
    }

    @Override
    public String toString() {
        return "that player is the monarch";
    }
}
