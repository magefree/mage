
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class HoodedBrawler extends CardImpl {

    public HoodedBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may exert Hooded Brawler as it attacks. When you do, it gets +2/+2 until end of turn.
        this.addAbility(new ExertAbility(new BecomesExertSourceTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn))));
    }

    private HoodedBrawler(final HoodedBrawler card) {
        super(card);
    }

    @Override
    public HoodedBrawler copy() {
        return new HoodedBrawler(this);
    }
}
