
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class SpiteMalice extends SplitCard {

    private static final FilterSpell filterNonCreatureSpell = new FilterSpell("noncreature spell");

    static {
        filterNonCreatureSpell.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    private static final FilterCreaturePermanent filterNonBlackCreature = new FilterCreaturePermanent("nonblack creature");

    static {
        filterNonBlackCreature.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SpiteMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}", "{3}{B}", SpellAbilityType.SPLIT);

        // Spite
        // Counter target noncreature spell.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell(filterNonCreatureSpell));

        // Malice
        // Destroy target nonblack creature. It can't be regenerated.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(filterNonBlackCreature));
    }

    public SpiteMalice(final SpiteMalice card) {
        super(card);
    }

    @Override
    public SpiteMalice copy() {
        return new SpiteMalice(this);
    }
}
