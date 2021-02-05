
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StromkirkCondemned extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampires you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public StromkirkCondemned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Discard a card: Vampires you control get +1/+1 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), new DiscardCardCost()));

    }

    private StromkirkCondemned(final StromkirkCondemned card) {
        super(card);
    }

    @Override
    public StromkirkCondemned copy() {
        return new StromkirkCondemned(this);
    }
}
