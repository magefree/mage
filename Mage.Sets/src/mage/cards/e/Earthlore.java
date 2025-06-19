package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Earthlore extends CardImpl {

    private static final FilterPermanent filterUntapped = new FilterPermanent("enchanted land is untapped");

    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filterUntapped);

    public Earthlore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Tap enchanted land: Target blocking creature gets +1/+2 until end of turn. Activate this ability only if enchanted land is untapped.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new BoostTargetEffect(1, 2),
                new TapAttachedCost().setText("Tap enchanted land"), condition
        );
        ability.addTarget(new TargetPermanent(new FilterBlockingCreature("blocking creature")));
        this.addAbility(ability);
    }

    private Earthlore(final Earthlore card) {
        super(card);
    }

    @Override
    public Earthlore copy() {
        return new Earthlore(this);
    }
}
