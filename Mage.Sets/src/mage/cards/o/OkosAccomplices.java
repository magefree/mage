package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkosAccomplices extends CardImpl {

    public OkosAccomplices(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private OkosAccomplices(final OkosAccomplices card) {
        super(card);
    }

    @Override
    public OkosAccomplices copy() {
        return new OkosAccomplices(this);
    }
}
