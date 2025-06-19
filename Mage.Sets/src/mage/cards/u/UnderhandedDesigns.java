package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UnderhandedDesigns extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control two or more artifacts"),
            ComparisonType.MORE_THAN, 1
    );

    public UnderhandedDesigns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever an artifact you control enters, you may pay {1}. If you do, each opponent loses 1 life and you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DoIfCostPaid(new LoseLifeOpponentsEffect(1), new GenericManaCost(1))
                        .addEffect(new GainLifeEffect(1).concatBy("and")),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ));

        // {1}{B}, Sacrifice Underhanded Designs: Destroy target creature. Activate this ability only if you control two or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{B}"), condition
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private UnderhandedDesigns(final UnderhandedDesigns card) {
        super(card);
    }

    @Override
    public UnderhandedDesigns copy() {
        return new UnderhandedDesigns(this);
    }
}
