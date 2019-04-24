
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DissensionInTheRanks extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking creature");

    static {
        filter.add(new BlockingPredicate());
    }

    public DissensionInTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Target blocking creature fights another target blocking creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent(1, 1, filter, false);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another target blocking creature");
        filter2.add(new AnotherTargetPredicate(2));
        filter2.add(new BlockingPredicate());
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    public DissensionInTheRanks(final DissensionInTheRanks card) {
        super(card);
    }

    @Override
    public DissensionInTheRanks copy() {
        return new DissensionInTheRanks(this);
    }
}
