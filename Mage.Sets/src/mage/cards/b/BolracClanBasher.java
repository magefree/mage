package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BolracClanBasher extends CardImpl {

    public BolracClanBasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Disguise {3}{R}{R}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private BolracClanBasher(final BolracClanBasher card) {
        super(card);
    }

    @Override
    public BolracClanBasher copy() {
        return new BolracClanBasher(this);
    }
}
