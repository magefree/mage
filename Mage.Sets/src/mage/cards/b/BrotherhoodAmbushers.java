package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrotherhoodAmbushers extends CardImpl {

    public BrotherhoodAmbushers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Freerunning {3}{B}
        this.addAbility(new FreerunningAbility("{3}{B}"));
    }

    private BrotherhoodAmbushers(final BrotherhoodAmbushers card) {
        super(card);
    }

    @Override
    public BrotherhoodAmbushers copy() {
        return new BrotherhoodAmbushers(this);
    }
}
