
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class NaturesChosen extends CardImpl {

    private static final FilterPermanent filterPermanent = new FilterPermanent("artifact, creature or land");

    static {
        filterPermanent.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    private static final FilterCreaturePermanent filterWhiteUntappedCreature = new FilterCreaturePermanent(" if enchanted creature is white and is untapped");
    
    static {
        filterWhiteUntappedCreature.add(new ColorPredicate(ObjectColor.WHITE));
        filterWhiteUntappedCreature.add(Predicates.not(new TappedPredicate()));
    }

    public NaturesChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {0}: Untap enchanted creature. Activate this ability only during your turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new UntapEnchantedEffect(), new GenericManaCost(0), 1, MyTurnCondition.instance));

        // Tap enchanted creature: Untap target artifact, creature, or land. Activate this ability only if enchanted creature is white and is untapped and only once each turn.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target artifact, creature, or land");
        LimitedTimesPerTurnActivatedAbility ability2 = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, effect, new TapAttachedCost(), 1, new AttachedToMatchesFilterCondition(filterWhiteUntappedCreature));
        ability2.addTarget(new TargetPermanent(filterPermanent));
        this.addAbility(ability2);
    }

    public NaturesChosen(final NaturesChosen card) {
        super(card);
    }

    @Override
    public NaturesChosen copy() {
        return new NaturesChosen(this);
    }
}
