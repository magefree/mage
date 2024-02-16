package mage.cards.b;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author weirddan455
 */
public final class BrotherhoodsEnd extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker");

    private static final FilterPermanent filter2
            = new FilterArtifactPermanent("artifacts with mana value 3 or less");

    static {
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BrotherhoodsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Choose one --
        // * Brotherhood's End deals 3 damage to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, filter));

        // * Destroy all artifacts with mana value 3 or less.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(filter2)));
    }

    private BrotherhoodsEnd(final BrotherhoodsEnd card) {
        super(card);
    }

    @Override
    public BrotherhoodsEnd copy() {
        return new BrotherhoodsEnd(this);
    }
}
