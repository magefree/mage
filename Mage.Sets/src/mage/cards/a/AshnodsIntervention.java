package mage.cards.a;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class AshnodsIntervention extends CardImpl {

    public AshnodsIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies or is put into exile from the battlefield, return it to its owner's hand."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0)
                .setText("until end of turn, target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new AshnodsInterventionAbility())
                .setText("and gains \"When this creature dies or is put into exile from the battlefield, return it to its owner's hand.\""));
    }

    private AshnodsIntervention(final AshnodsIntervention card) {
        super(card);
    }

    @Override
    public AshnodsIntervention copy() {
        return new AshnodsIntervention(this);
    }
}

class AshnodsInterventionAbility extends TriggeredAbilityImpl {

    public AshnodsInterventionAbility() {
        super(Zone.ALL, new ReturnToHandSourceEffect());
        setLeavesTheBattlefieldTrigger(true);
    }

    private AshnodsInterventionAbility(final AshnodsInterventionAbility ability) {
        super(ability);
    }

    @Override
    public AshnodsInterventionAbility copy() {
        return new AshnodsInterventionAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getTargetId().equals(sourceId) && zEvent.getFromZone() == Zone.BATTLEFIELD
                && (zEvent.getToZone() == Zone.GRAVEYARD || zEvent.getToZone() == Zone.EXILED);
    }

    @Override
    public String getRule() {
        return "When {this} dies or is put into exile from the battlefield, return it to its owner's hand";
    }
}
