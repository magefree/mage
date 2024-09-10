package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestilentSyphoner extends CardImpl {

    public PestilentSyphoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));
    }

    private PestilentSyphoner(final PestilentSyphoner card) {
        super(card);
    }

    @Override
    public PestilentSyphoner copy() {
        return new PestilentSyphoner(this);
    }
}
