package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WraithViciousVigilante extends CardImpl {

    public WraithViciousVigilante(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Fear Gas -- Wraith can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility().withFlavorWord("Fear Gas"));
    }

    private WraithViciousVigilante(final WraithViciousVigilante card) {
        super(card);
    }

    @Override
    public WraithViciousVigilante copy() {
        return new WraithViciousVigilante(this);
    }
}
