
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class CruxOfFate extends CardImpl {

    private static final FilterCreaturePermanent filterNonDragon = new FilterCreaturePermanent("non-Dragon creatures");

    static {
        filterNonDragon.add(Predicates.not(new SubtypePredicate(SubType.DRAGON)));
    }

    public CruxOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Choose one -
        // * Destroy all Dragon creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterCreaturePermanent(SubType.DRAGON, "Dragon creatures")));
        // * Destroy all non-Dragon creatures.
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyAllEffect(new FilterCreaturePermanent(filterNonDragon)));
        this.getSpellAbility().addMode(mode);
    }

    public CruxOfFate(final CruxOfFate card) {
        super(card);
    }

    @Override
    public CruxOfFate copy() {
        return new CruxOfFate(this);
    }
}
