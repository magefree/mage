package mage.cards.r;

import mage.MageInt;
import mage.abilities.AbilityImpl;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReflectiveGolem extends CardImpl {

    public ReflectiveGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell that targets only Reflective Golem, you may pay {2}. If you do, copy that spell. You may choose new targets for the copy.
        this.addAbility(new ReflectiveGolemTriggeredAbility());
    }

    private ReflectiveGolem(final ReflectiveGolem card) {
        super(card);
    }

    @Override
    public ReflectiveGolem copy() {
        return new ReflectiveGolem(this);
    }
}

class ReflectiveGolemTriggeredAbility extends TriggeredAbilityImpl {

    ReflectiveGolemTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CopyTargetSpellEffect(), new GenericManaCost(2)), false);
    }

    private ReflectiveGolemTriggeredAbility(final ReflectiveGolemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ReflectiveGolemTriggeredAbility copy() {
        return new ReflectiveGolemTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpellOrLKIStack(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        if (spell.getSpellAbilities()
                .stream()
                .map(AbilityImpl::getModes)
                .flatMap(m -> m.getSelectedModes().stream().map(m::get))
                .filter(Objects::nonNull)
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .filter(t -> !t.isNotTarget())
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .distinct()
                .filter(getSourceId()::equals)
                .count() != 1) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(spell, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that targets only {this}, " +
                "you may pay {2}. If you do, copy that spell. You may choose new targets for the copy.";
    }
}
