package mage.cards.b;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BrokenDam extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without horsemanship");

    static {
        filter.add(Predicates.not(new AbilityPredicate(HorsemanshipAbility.class)));
    }

    public BrokenDam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Tap one or two target creatures without horsemanship.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap one or two target creatures without horsemanship"));
        this.getSpellAbility().addTarget(new TargetPermanent(1, 2, filter));
    }

    private BrokenDam(final BrokenDam card) {
        super(card);
    }

    @Override
    public BrokenDam copy() {
        return new BrokenDam(this);
    }
}
