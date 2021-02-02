
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author North
 */
public final class SalvageSlasher extends CardImpl {

    public SalvageSlasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Salvage Slasher gets +1/+0 for each artifact card in your graveyard.
        BoostSourceEffect effect = new BoostSourceEffect(new CardsInControllerGraveyardCount(new FilterArtifactCard()),
                StaticValue.get(0),
                Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private SalvageSlasher(final SalvageSlasher card) {
        super(card);
    }

    @Override
    public SalvageSlasher copy() {
        return new SalvageSlasher(this);
    }
}
