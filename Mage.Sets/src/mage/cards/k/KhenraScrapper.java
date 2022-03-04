
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class KhenraScrapper extends CardImpl {

    public KhenraScrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // You may exert Khenra Scrapper as it attacks. When you do, it gets +2/+0 until end of turn.
        this.addAbility(new ExertAbility(new BecomesExertSourceTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn))));
    }

    private KhenraScrapper(final KhenraScrapper card) {
        super(card);
    }

    @Override
    public KhenraScrapper copy() {
        return new KhenraScrapper(this);
    }
}
