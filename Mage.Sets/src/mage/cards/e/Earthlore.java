
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Earthlore extends CardImpl {

    private static final FilterControlledPermanent filterLand = new FilterControlledPermanent("land you control");

    static {
        filterLand.add(CardType.LAND.getPredicate());
    }

    private static final FilterPermanent filterUntapped = new FilterPermanent("enchanted land is untapped");
    
    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    public Earthlore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetControlledPermanent(filterLand);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Tap enchanted land: Target blocking creature gets +1/+2 until end of turn. Activate this ability only if enchanted land is untapped.
        Cost cost = new TapAttachedCost();
        cost.setText("Tap enchanted land");
        Ability ability2 = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 2, Duration.EndOfTurn), cost, new AttachedToMatchesFilterCondition(filterUntapped));
        ability2.addTarget(new TargetCreaturePermanent(new FilterBlockingCreature("blocking creature")));
        this.addAbility(ability2);

    }

    private Earthlore(final Earthlore card) {
        super(card);
    }

    @Override
    public Earthlore copy() {
        return new Earthlore(this);
    }
}
