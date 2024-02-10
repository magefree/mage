package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class AegisOfTheLegion extends CardImpl {

    public AegisOfTheLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has mentor.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(new MentorAbility(), AttachmentType.EQUIPMENT)
                .setText("and has mentor. <i>(Whenever it attacks, put a +1/+1 counter on target attacking creature with lesser power.)</i>"));
        this.addAbility(ability);

        // Whenever equipped creature mentors a creature, put a shield counter on that creature.
        this.addAbility(new AegisOfTheLegionTriggeredAbility());

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private AegisOfTheLegion(final AegisOfTheLegion card) {
        super(card);
    }

    @Override
    public AegisOfTheLegion copy() {
        return new AegisOfTheLegion(this);
    }
}

class AegisOfTheLegionTriggeredAbility extends TriggeredAbilityImpl {

    AegisOfTheLegionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.SHIELD.createInstance()));
        setTriggerPhrase("Whenever equipped creature mentors a creature, ");
    }

    private AegisOfTheLegionTriggeredAbility(final AegisOfTheLegionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MENTORED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // 20240202 - 702.134c
        // An ability that triggers whenever a creature mentors another creature
        // triggers whenever a mentor ability whose source is the first creature and whose target is the second creature resolves.
        Permanent attachment = getSourcePermanentOrLKI(game);
        Permanent mentoredCreature = game.getPermanent(event.getTargetId());
        if (attachment == null || mentoredCreature == null || !event.getSourceId().equals(attachment.getAttachedTo())) {
            return false;
        }

        getEffects().setTargetPointer(new FixedTarget(mentoredCreature, game));
        return true;
    }

    @Override
    public AegisOfTheLegionTriggeredAbility copy() {
        return new AegisOfTheLegionTriggeredAbility(this);
    }
}
