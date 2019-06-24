package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningStormkin extends CardImpl {

    public LightningStormkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private LightningStormkin(final LightningStormkin card) {
        super(card);
    }

    @Override
    public LightningStormkin copy() {
        return new LightningStormkin(this);
    }
}
