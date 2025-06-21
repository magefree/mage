package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NukaNukeLauncher extends CardImpl {

    public NukaNukeLauncher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0 and has intimidate.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                IntimidateAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has intimidate"));
        this.addAbility(ability);

        // Whenever equipped creature attacks, until the end of defending player's next turn, that player gets two rad counters whenever they cast a spell.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(new NukaNukeLauncherDelayedTriggeredAbility()),
                AttachmentType.EQUIPMENT, false, SetTargetPointer.PLAYER
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private NukaNukeLauncher(final NukaNukeLauncher card) {
        super(card);
    }

    @Override
    public NukaNukeLauncher copy() {
        return new NukaNukeLauncher(this);
    }
}

class NukaNukeLauncherDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID playerId = null;

    NukaNukeLauncherDelayedTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.RAD.createInstance(2)), Duration.Custom, false, false);
    }

    private NukaNukeLauncherDelayedTriggeredAbility(final NukaNukeLauncherDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NukaNukeLauncherDelayedTriggeredAbility copy() {
        return new NukaNukeLauncherDelayedTriggeredAbility(this);
    }

    @Override
    public void init(Game game) {
        super.init(game);
        this.playerId = this
                .getEffects()
                .stream()
                .map(Effect::getTargetPointer)
                .map(targetPointer -> targetPointer.getFirst(game, this))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean isInactive(Game game) {
        if (playerId == null) {
            return true;
        }
        if (game.isActivePlayer(playerId)) {
            this.setDuration(Duration.EndOfTurn);
        }
        return super.isInactive(game);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && spell.isControlledBy(playerId);
    }

    @Override
    public String getRule() {
        return "Until the end of defending player's next turn, that player gets two rad counters whenever they cast a spell";
    }
}
