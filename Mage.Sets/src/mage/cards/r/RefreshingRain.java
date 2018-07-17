
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class RefreshingRain extends CardImpl {

    private static final FilterPermanent filterForest = new FilterPermanent();
    private static final FilterPermanent filterSwamp = new FilterPermanent();

    static {
        filterForest.add(new SubtypePredicate(SubType.FOREST));
        filterSwamp.add(new SubtypePredicate(SubType.SWAMP));
    }

    public RefreshingRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // If an opponent controls a Swamp and you control a Forest, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Swamp and you control a Forest",
                new OpponentControlsPermanentCondition(filterSwamp),
                new PermanentsOnTheBattlefieldCondition(filterForest));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));

        // Target player gains 6 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public RefreshingRain(final RefreshingRain card) {
        super(card);
    }

    @Override
    public RefreshingRain copy() {
        return new RefreshingRain(this);
    }
}
