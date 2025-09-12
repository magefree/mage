package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrenziedGorespawn extends CardImpl {

    public FrenziedGorespawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Frenzied Gorespawn enters the battlefield, for each opponent, goad target creature that player controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoadTargetEffect()
                .setText("for each opponent, goad target creature that player controls"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // Whenever one or more creatures attack one of your opponents, those creatures gain menace until end of turn.
        this.addAbility(new FrenziedGorespawnTriggeredAbility());
    }

    private FrenziedGorespawn(final FrenziedGorespawn card) {
        super(card);
    }

    @Override
    public FrenziedGorespawn copy() {
        return new FrenziedGorespawn(this);
    }
}

class FrenziedGorespawnTriggeredAbility extends TriggeredAbilityImpl {

    FrenziedGorespawnTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new MenaceAbility(false))
                .setText("those creatures gain menace until end of turn"));
        this.setTriggerPhrase("Whenever one or more creatures attack one of your opponents, ");
    }

    private FrenziedGorespawnTriggeredAbility(final FrenziedGorespawnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrenziedGorespawnTriggeredAbility copy() {
        return new FrenziedGorespawnTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(((DefenderAttackedEvent) event).getAttackers(game), game));
        return true;
    }
}
