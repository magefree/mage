package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author BursegSardaukar
 */
public final class StinkdrinkerBandit extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.ROGUE, "a Rogue you control");

    public StinkdrinkerBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowl {1}, {B} (You may cast this for its prowl cost if you dealt combat damage to a player this turn with a Goblin or Rogue.)
        this.addAbility(new ProwlAbility("{1}{B}"));

        // Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn.
        this.addAbility(new AttacksAndIsNotBlockedAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(2, 1, Duration.EndOfTurn),
                filter, false, SetTargetPointer.PERMANENT
        ));
    }

    private StinkdrinkerBandit(final StinkdrinkerBandit card) {
        super(card);
    }

    @Override
    public StinkdrinkerBandit copy() {
        return new StinkdrinkerBandit(this);
    }
}