
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class AuraOfSilence extends CardImpl {
    public AuraOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // Artifact and enchantment spells your opponents cast cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AuraOfSilenceCostModificationEffect()));
        
        // Sacrifice Aura of Silence: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    public AuraOfSilence(final AuraOfSilence card) {
        super(card);
    }

    @Override
    public AuraOfSilence copy() {
        return new AuraOfSilence(this);
    }
}

class AuraOfSilenceCostModificationEffect extends CostModificationEffectImpl {

    AuraOfSilenceCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Artifact and enchantment spells your opponents cast cost {2} more to cast";
    }

    AuraOfSilenceCostModificationEffect(AuraOfSilenceCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                Card card = game.getCard(abilityToModify.getSourceId());
                if (card != null && (card.isArtifact() || card.isEnchantment())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AuraOfSilenceCostModificationEffect copy() {
        return new AuraOfSilenceCostModificationEffect(this);
    }
}