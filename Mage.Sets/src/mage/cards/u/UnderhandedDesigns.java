
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class UnderhandedDesigns extends CardImpl {

    public UnderhandedDesigns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // Whenever an artifact enters the battlefield under your control, you may pay {1}. If you do, each opponent loses 1 life and you gain 1 life.
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(new LoseLifeOpponentsEffect(1), new GenericManaCost(1));
        Effect effect = new GainLifeEffect(1);
        doIfCostPaid.addEffect(effect);
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, doIfCostPaid, new FilterControlledArtifactPermanent("an artifact"), false,
                "Whenever an artifact enters the battlefield under your control, you may pay {1}. If you do, each opponent loses 1 life and you gain 1 life."));

        // {1}{B}, Sacrifice Underhanded Designs: Destroy target creature. Activate this ability only if you control two or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new DestroyTargetEffect(),
                new ManaCostsImpl<>("{1}{B}"),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent("you control two or more artifacts"), ComparisonType.MORE_THAN, 1));
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
