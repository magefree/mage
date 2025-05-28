package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMasamune extends CardImpl {

    public TheMasamune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // As long as equipped creature is attacking, it has first strike and must be blocked if able.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT),
                AttachedAttackingCondition.instance, "as long as equipped creature is attacking, it has first strike"
        ));
        ability.addEffect(new ConditionalRequirementEffect(
                new MustBeBlockedByAtLeastOneAttachedEffect(),
                AttachedAttackingCondition.instance, "and must be blocked if able"
        ));
        this.addAbility(ability);

        // Equipped creature has "If a creature dying causes a triggered ability of this creature or an emblem you own to trigger, that ability triggers an additional time."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new TheMasamuneEffect()), AttachmentType.EQUIPMENT
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private TheMasamune(final TheMasamune card) {
        super(card);
    }

    @Override
    public TheMasamune copy() {
        return new TheMasamune(this);
    }
}

class TheMasamuneEffect extends ReplacementEffectImpl {

    TheMasamuneEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature dying causes a triggered ability of this creature " +
                "or an emblem you own to trigger, that ability triggers an additional time.";
    }

    private TheMasamuneEffect(final TheMasamuneEffect effect) {
        super(effect);
    }

    @Override
    public TheMasamuneEffect copy() {
        return new TheMasamuneEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return Optional
                .ofNullable(event)
                .filter(NumberOfTriggersEvent.class::isInstance)
                .map(NumberOfTriggersEvent.class::cast)
                .map(NumberOfTriggersEvent::getSourceEvent)
                .filter(ZoneChangeEvent.class::isInstance)
                .map(ZoneChangeEvent.class::cast)
                .filter(ZoneChangeEvent::isDiesEvent)
                .map(ZoneChangeEvent::getTarget)
                .map(permanent -> permanent.isCreature(game))
                .orElse(false)
                && source.isControlledBy(event.getPlayerId())
                && (source.getSourceId().equals(event.getSourceId())
                || Optional
                .ofNullable(event)
                .map(GameEvent::getSourceId)
                .map(game::getEmblem)
                .map(Emblem.class::cast)
                .map(Emblem::getControllerOrOwnerId)
                .map(source::isControlledBy)
                .orElse(false));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
