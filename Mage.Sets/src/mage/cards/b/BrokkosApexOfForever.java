package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author vernonross21
 */
public final class BrokkosApexOfForever extends CardImpl {

    public BrokkosApexOfForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mutate {2}{U/B}{G}{G}
        this.addAbility(new MutateAbility(this, "{2}{U/B}{G}{G}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // You may cast this card from your graveyard using its mutate ability.
        // This ability is automatically handled by MutateAbility which adds alternate casting from graveyard
    }

    private BrokkosApexOfForever(final BrokkosApexOfForever card) {
        super(card);
    }

    @Override
    public BrokkosApexOfForever copy() {
        return new BrokkosApexOfForever(this);
    }
}



