package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcboundPrototype extends CardImpl {

    public ArcboundPrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular 2
        this.addAbility(new ModularAbility(this, 2));
    }

    private ArcboundPrototype(final ArcboundPrototype card) {
        super(card);
    }

    @Override
    public ArcboundPrototype copy() {
        return new ArcboundPrototype(this);
    }
}
