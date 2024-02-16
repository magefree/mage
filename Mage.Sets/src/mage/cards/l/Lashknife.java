
package mage.cards.l;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class Lashknife extends CardImpl {

    private static final FilterControlledPermanent plainsFilter = new FilterControlledPermanent("If you control a Plains");
    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent("an untapped creature you control");

    static {
        plainsFilter.add(SubType.PLAINS.getPredicate());
        creatureFilter.add(TappedPredicate.UNTAPPED);
    }

    public Lashknife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // If you control a Plains, you may tap an untapped creature you control rather than pay Lashknife's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new TapTargetCost(new TargetControlledPermanent(creatureFilter)), new PermanentsOnTheBattlefieldCondition(plainsFilter)));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA)));
    }

    private Lashknife(final Lashknife card) {
        super(card);
    }

    @Override
    public Lashknife copy() {
        return new Lashknife(this);
    }
}
