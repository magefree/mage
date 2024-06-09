package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RograkhSonOfRohgahh extends CardImpl {

    public RograkhSonOfRohgahh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{0}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOBOLD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private RograkhSonOfRohgahh(final RograkhSonOfRohgahh card) {
        super(card);
    }

    @Override
    public RograkhSonOfRohgahh copy() {
        return new RograkhSonOfRohgahh(this);
    }
}
