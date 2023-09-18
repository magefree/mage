package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingsteedTrainer extends CardImpl {

    public WingsteedTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Wingsteed Trainer enters the battlefield or attacks, conjure a Stormfront Pegasus card into your hand.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ConjureCardEffect("Stormfront Pegasus")));
    }

    private WingsteedTrainer(final WingsteedTrainer card) {
        super(card);
    }

    @Override
    public WingsteedTrainer copy() {
        return new WingsteedTrainer(this);
    }
}
