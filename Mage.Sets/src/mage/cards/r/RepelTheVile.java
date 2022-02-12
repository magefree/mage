package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RepelTheVile extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public RepelTheVile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Choose one —
        // • Exile target creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Exile target enchantment.
        Mode mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private RepelTheVile(final RepelTheVile card) {
        super(card);
    }

    @Override
    public RepelTheVile copy() {
        return new RepelTheVile(this);
    }
}
