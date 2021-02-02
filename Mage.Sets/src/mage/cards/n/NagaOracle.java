
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author stravant
 */
public final class NagaOracle extends CardImpl {

    public NagaOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Naga Oracle enters the battlefield, look at the top three cards of your library. Put any number of them into your graveyard
        // and the rest back on top of your library in any order.
        Effect effect = new LookLibraryAndPickControllerEffect(
                        /* oh god, Microsoft looks conservative with their function parameters in comparison */
                        StaticValue.get(3),
                        false,
                        StaticValue.get(3),
                        new FilterCard("cards"),
                        Zone.LIBRARY,
                        true,
                        false,
                        true,
                        Zone.GRAVEYARD,
                        false);
        effect.setText("look at the top three cards of your library. Put any number of them into your graveyard "
                + "and the rest back on top of your library in any order");
        addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private NagaOracle(final NagaOracle card) {
        super(card);
    }

    @Override
    public NagaOracle copy() {
        return new NagaOracle(this);
    }
}
