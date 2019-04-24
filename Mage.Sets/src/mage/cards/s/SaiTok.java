package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author NinthWorld
 */
public final class SaiTok extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker if it has converted mana cost 4 or less");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 5));
    }

    public SaiTok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");
        

        // Destroy target creature or planeswalker if it has converted mana cost 4 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public SaiTok(final SaiTok card) {
        super(card);
    }

    @Override
    public SaiTok copy() {
        return new SaiTok(this);
    }
}
