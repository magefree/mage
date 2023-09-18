
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2 & L_J
 */
public final class Seeker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by artifact creatures and/or white creatures");

    static {
        filter.add(Predicates.not(
                Predicates.or(
                        Predicates.and(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()),
                        Predicates.and(CardType.CREATURE.getPredicate(), new ColorPredicate(ObjectColor.WHITE)
                        ))));
    }

    public Seeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't be blocked except by artifact creatures and/or white creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, filter, AttachmentType.AURA)));

    }

    private Seeker(final Seeker card) {
        super(card);
    }

    @Override
    public Seeker copy() {
        return new Seeker(this);
    }
}
