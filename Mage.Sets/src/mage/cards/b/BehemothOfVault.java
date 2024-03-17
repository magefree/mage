package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class BehemothOfVault extends CardImpl {

    public BehemothOfVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Behemoth of Vault 0 enters the battlefield, you get {E}{E}{E}{E}.
        // When Behemoth of Vault 0 dies, you may pay an amount of {E} equal to target nonland permanent's mana value. When you do, destroy that permanent.
    }

    private BehemothOfVault(final BehemothOfVault card) {
        super(card);
    }

    @Override
    public BehemothOfVault copy() {
        return new BehemothOfVault(this);
    }
}
