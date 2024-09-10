package mage.cards.l;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazotepChancellor extends CardImpl {

    public LazotepChancellor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you discard a card, you may pay {1}. If you do, amass 2.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new DoIfCostPaid(new AmassEffect(2, SubType.ZOMBIE), new GenericManaCost(1)), false
        ));
    }

    private LazotepChancellor(final LazotepChancellor card) {
        super(card);
    }

    @Override
    public LazotepChancellor copy() {
        return new LazotepChancellor(this);
    }
}
