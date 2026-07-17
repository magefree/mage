package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutlandLiberator extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public OutlandLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{G}",
                "Frenzied Trapbreaker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        // Outland Liberator
        this.getLeftHalfCard().setPT(2, 2);

        // {1}, Sacrifice Outland Liberator: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Frenzied Trapbreaker
        this.getRightHalfCard().setPT(3, 3);

        // {1}, Sacrifice Frenzied Trapbreaker: Destroy target artifact or enchantment.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getRightHalfCard().addAbility(ability);

        // Whenever Frenzied Trapbreaker attacks, destroy target artifact or enchantment defending player controls.
        ability = new AttacksTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private OutlandLiberator(final OutlandLiberator card) {
        super(card);
    }

    @Override
    public OutlandLiberator copy() {
        return new OutlandLiberator(this);
    }
}
