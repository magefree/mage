
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author ilcartographer
 */
public final class StranglingSoot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with toughness 3 or less");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public StranglingSoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Destroy target creature with toughness 3 or less.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        // Flashback {5}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{R}")));
    }

    private StranglingSoot(final StranglingSoot card) {
        super(card);
    }

    @Override
    public StranglingSoot copy() {
        return new StranglingSoot(this);
    }
}
