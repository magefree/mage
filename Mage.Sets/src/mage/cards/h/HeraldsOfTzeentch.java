package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldsOfTzeentch extends CardImpl {

    public HeraldsOfTzeentch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private HeraldsOfTzeentch(final HeraldsOfTzeentch card) {
        super(card);
    }

    @Override
    public HeraldsOfTzeentch copy() {
        return new HeraldsOfTzeentch(this);
    }
}
