package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Psychomancer extends CardImpl {

    public Psychomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.NECRON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Harbinger of Despair -- Whenever Psychomancer or another nontoken artifact you control is put into a graveyard from the battlefield or is put into exile from the battlefield, target opponent loses 1 life and you gain 1 life.
        this.addAbility(new PsychomancerTriggeredAbility());
    }

    private Psychomancer(final Psychomancer card) {
        super(card);
    }

    @Override
    public Psychomancer copy() {
        return new Psychomancer(this);
    }
}

class PsychomancerTriggeredAbility extends TriggeredAbilityImpl {

    PsychomancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
        this.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addTarget(new TargetOpponent());
        this.setTriggerPhrase("Whenever {this} or another nontoken artifact you control is put " +
                "into a graveyard from the battlefield or is put into exile from the battlefield, ");
        this.withFlavorWord("Harbinger of Despair");
    }

    private PsychomancerTriggeredAbility(final PsychomancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychomancerTriggeredAbility copy() {
        return new PsychomancerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getFromZone() == Zone.BATTLEFIELD
                && (zEvent.getToZone() == Zone.GRAVEYARD || zEvent.getToZone() == Zone.EXILED)
                && zEvent.getTarget() != null
                && (zEvent.getTargetId().equals(getSourceId())
                || zEvent.getTarget().isArtifact(game) && !(zEvent.getTarget() instanceof PermanentToken));
    }
}
