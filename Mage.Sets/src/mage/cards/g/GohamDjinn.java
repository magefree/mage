
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class GohamDjinn extends CardImpl {

    public GohamDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}{B}: Regenerate Goham Djinn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));

        // Goham Djinn gets -2/-2 as long as black is the most common color among all permanents or is tied for most common.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(-2, -2, Duration.WhileOnBattlefield),
                        new MostCommonColorCondition(new ObjectColor(ObjectColor.BLACK)),
                        "{this} gets -2/-2 as long as black is the most common color among all permanents or is tied for most common")));
    }

    private GohamDjinn(final GohamDjinn card) {
        super(card);
    }

    @Override
    public GohamDjinn copy() {
        return new GohamDjinn(this);
    }
}
