package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class BrokkosApexOfForever extends CardImpl {

    public BrokkosApexOfForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {2}{U/B}{G}{G}
        this.addAbility(new MutateAbility(this, "{2}{U/B}{G}{G}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // You may cast Brokkos, Apex of Forever from your graveyard using its mutate ability.
        this.addAbility(new MutateAbility(this, "{2}{U/B}{G}{G}", Zone.GRAVEYARD));
    }

    private BrokkosApexOfForever(final BrokkosApexOfForever card) {
        super(card);
    }

    @Override
    public BrokkosApexOfForever copy() {
        return new BrokkosApexOfForever(this);
    }
}
