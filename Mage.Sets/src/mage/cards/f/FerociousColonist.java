package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author muz
 */
public final class FerociousColonist extends CardImpl {

    public FerociousColonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Melee
        this.addAbility(new MeleeAbility());
    }

    private FerociousColonist(final FerociousColonist card) {
        super(card);
    }

    @Override
    public FerociousColonist copy() {
        return new FerociousColonist(this);
    }
}
