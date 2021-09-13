package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonedCathar extends CardImpl {

    public SeasonedCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.nightCard = true;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private SeasonedCathar(final SeasonedCathar card) {
        super(card);
    }

    @Override
    public SeasonedCathar copy() {
        return new SeasonedCathar(this);
    }
}
