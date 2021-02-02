
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInAnyLibraryCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class IslebackSpawn extends CardImpl {

    public IslebackSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(8);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        // Isleback Spawn gets +4/+8 as long as a library has twenty or fewer cards in it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalContinuousEffect(
                new BoostSourceEffect(4,8, Duration.EndOfGame),
                new CardsInAnyLibraryCondition(ComparisonType.FEWER_THAN, 21),
                "{this} gets +4/+8 as long as a library has twenty or fewer cards in it")));
    }

    private IslebackSpawn(final IslebackSpawn card) {
        super(card);
    }

    @Override
    public IslebackSpawn copy() {
        return new IslebackSpawn(this);
    }
}
