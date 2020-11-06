package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoardingParty extends CardImpl {

    public BoardingParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private BoardingParty(final BoardingParty card) {
        super(card);
    }

    @Override
    public BoardingParty copy() {
        return new BoardingParty(this);
    }
}
