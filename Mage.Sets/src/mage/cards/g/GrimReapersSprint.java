package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsMainPhaseCondition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class GrimReapersSprint extends CardImpl {

    public GrimReapersSprint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        
        this.subtype.add(SubType.AURA);

        // Morbid -- This spell costs {3} less to cast if a creature died this turn.
        this.addAbility(
                new SimpleStaticAbility(Zone.ALL, new GrimReapersSprintCostModificationEffect())
                        .addHint(MorbidHint.instance)
                        .setAbilityWord(AbilityWord.MORBID)
        );

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Grim Reaper's Sprint enters the battlefield, untap each creature you control. If it's your main phase, there is an additional combat phase after this phase.
        Ability triggeredAbility = new EntersBattlefieldTriggeredAbility(
                new UntapAllControllerEffect(
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        "untap each creature you control"
                ), false
        );
        triggeredAbility.addEffect(new ConditionalOneShotEffect(new AdditionalCombatPhaseEffect(), IsMainPhaseCondition.YOUR, "If it's your main phase, there is an additional combat phase after this phase."));
        this.addAbility(triggeredAbility);

        // Enchanted creature gets +2/+2 and has haste.
        Effect effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has haste");
        Ability staticAbility = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        staticAbility.addEffect(effect);
        this.addAbility(staticAbility);
    }

    private GrimReapersSprint(final GrimReapersSprint card) {
        super(card);
    }

    @Override
    public GrimReapersSprint copy() {
        return new GrimReapersSprint(this);
    }
}

class GrimReapersSprintCostModificationEffect extends CostModificationEffectImpl {

    GrimReapersSprintCostModificationEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {3} less to cast if a creature died this turn";
    }

    private GrimReapersSprintCostModificationEffect(final GrimReapersSprintCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId())
                && (abilityToModify instanceof SpellAbility)) {
            return MorbidCondition.instance.apply(game, abilityToModify);
        }
        return false;
    }

    @Override
    public GrimReapersSprintCostModificationEffect copy() {
        return new GrimReapersSprintCostModificationEffect(this);
    }
}