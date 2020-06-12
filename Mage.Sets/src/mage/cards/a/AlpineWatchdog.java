package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpineWatchdog extends CardImpl {

    public AlpineWatchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private AlpineWatchdog(final AlpineWatchdog card) {
        super(card);
    }

    @Override
    public AlpineWatchdog copy() {
        return new AlpineWatchdog(this);
    }
}
