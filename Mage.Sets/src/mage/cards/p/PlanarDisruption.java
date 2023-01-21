package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class PlanarDisruption extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("Artifact, creature, or planeswalker");

    static{
        filter.add(Predicates.or(CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()));
    }

    public PlanarDisruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant artifact, creature, or planeswalker
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));
        // Enchanted permanent can't attack or block, and it's activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(new CantBlockAttackActivateAttachedEffect("permanent")));
    }

    private PlanarDisruption(final PlanarDisruption card) {
        super(card);
    }

    @Override
    public PlanarDisruption copy() {
        return new PlanarDisruption(this);
    }
}
