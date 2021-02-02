
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class NagaVitalist extends CardImpl {

    public NagaVitalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any type that a land you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU, false));
    }

    private NagaVitalist(final NagaVitalist card) {
        super(card);
    }

    @Override
    public NagaVitalist copy() {
        return new NagaVitalist(this);
    }
}
