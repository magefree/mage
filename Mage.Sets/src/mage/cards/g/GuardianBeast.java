package mage.cards.g;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author spjspj
 */
public final class GuardianBeast extends CardImpl {

    private static final FilterObject filterAura = new FilterStackObject("auras");
    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("Noncreature artifacts");

    static {
        filterAura.add(CardType.ENCHANTMENT.getPredicate());
        filterAura.add(SubType.AURA.getPredicate());
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public GuardianBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As long as Guardian Beast is untapped, noncreature artifacts you control can't be enchanted, they're indestructible, and other players can't gain control of them.
        // This effect doesn't remove Auras already attached to those artifacts.
        Effect effect = new ConditionalContinuousEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                SourceTappedCondition.UNTAPPED,
                "As long as Guardian Beast is untapped, noncreature artifacts you control can't be enchanted, they're indestructible");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new GuardianBeastConditionalEffect(this.getId()));
        this.addAbility(ability);

    }

    private GuardianBeast(final GuardianBeast card) {
        super(card);
    }

    @Override
    public GuardianBeast copy() {
        return new GuardianBeast(this);
    }
}

class GuardianBeastConditionalEffect extends ContinuousRuleModifyingEffectImpl {

    private final UUID guardianBeastId;

    public GuardianBeastConditionalEffect(UUID guardianBeastId) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = ", and other players can't gain control of them. This effect doesn't remove Auras already attached to those artifacts";
        this.guardianBeastId = guardianBeastId;
    }

    public GuardianBeastConditionalEffect(final GuardianBeastConditionalEffect effect) {
        super(effect);
        this.guardianBeastId = effect.guardianBeastId;
    }

    @Override
    public GuardianBeastConditionalEffect copy() {
        return new GuardianBeastConditionalEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(event.getTargetId());
        Permanent guardianBeast = game.getPermanent(guardianBeastId);

        if (guardianBeast == null || guardianBeast.isTapped() || sourceObject == null || targetPermanent == null) {
            return false;
        }

        if (!Objects.equals(targetPermanent.getControllerId(), guardianBeast.getControllerId())) {
            return false;
        }

        StackObject spell = game.getStack().getStackObject(event.getSourceId());
        if (event.getType() == GameEvent.EventType.GAIN_CONTROL
                || ((event.getType() == GameEvent.EventType.ATTACH
                || event.getType() == GameEvent.EventType.TARGET)
                && spell != null && spell.isEnchantment(game) && spell.hasSubtype(SubType.AURA, game))) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_ARTIFACTS_NON_CREATURE, source.getControllerId(), game)) {
                if (perm != null && Objects.equals(perm.getId(), targetPermanent.getId()) && !perm.isCreature(game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
