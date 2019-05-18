package mage.cards.e;

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
public final class EnforcerGriffin extends CardImpl {

    public EnforcerGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private EnforcerGriffin(final EnforcerGriffin card) {
        super(card);
    }

    @Override
    public EnforcerGriffin copy() {
        return new EnforcerGriffin(this);
    }
}
