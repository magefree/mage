package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GutsplitterGang extends CardImpl {

    public GutsplitterGang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of your first main phase, you may blight 2. If you don't, you lose 3 life.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new DoIfCostPaid(
                null, new LoseLifeOpponentsEffect(3), new BlightCost(2)
        )));
    }

    private GutsplitterGang(final GutsplitterGang card) {
        super(card);
    }

    @Override
    public GutsplitterGang copy() {
        return new GutsplitterGang(this);
    }
}
