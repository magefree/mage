
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author fireshoes
 */
public final class StoneshakerShaman extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("untapped land");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public StoneshakerShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each player's end step, that player sacrifices an untapped land.
         this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeEffect(filter, 1, "that player"), TargetController.EACH_PLAYER, false));
    }

    private StoneshakerShaman(final StoneshakerShaman card) {
        super(card);
    }

    @Override
    public StoneshakerShaman copy() {
        return new StoneshakerShaman(this);
    }
}
