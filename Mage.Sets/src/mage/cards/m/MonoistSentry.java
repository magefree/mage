package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MonoistSentry extends CardImpl {

    public MonoistSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private MonoistSentry(final MonoistSentry card) {
        super(card);
    }

    @Override
    public MonoistSentry copy() {
        return new MonoistSentry(this);
    }
}
