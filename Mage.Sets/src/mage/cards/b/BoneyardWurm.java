package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author nantuko
 */
public final class BoneyardWurm extends CardImpl {

    public BoneyardWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.WURM);

        // Boneyard Wurm's power and toughness are each equal to the number of creature cards in your graveyard.
        DynamicValue value = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(value, Duration.EndOfGame)));
    }

    private BoneyardWurm(final BoneyardWurm card) {
        super(card);
    }

    @Override
    public BoneyardWurm copy() {
        return new BoneyardWurm(this);
    }
}
