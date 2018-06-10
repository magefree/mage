
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 * @author LevelX2
 */
public final class GrowingRanks extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GrowingRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G/W}{G/W}");


        // At the beginning of your upkeep, populate. (Create a token that's a copy of a creature token you control.)
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PopulateEffect(""), TargetController.YOU, false));
    }

    public GrowingRanks(final GrowingRanks card) {
        super(card);
    }

    @Override
    public GrowingRanks copy() {
        return new GrowingRanks(this);
    }
}
