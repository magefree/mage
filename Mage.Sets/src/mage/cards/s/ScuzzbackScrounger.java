package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScuzzbackScrounger extends CardImpl {

    public ScuzzbackScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of your first main phase, you may blight 1. If you do, create a Treasure token.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new TreasureToken()), new BlightCost(1))
        ));
    }

    private ScuzzbackScrounger(final ScuzzbackScrounger card) {
        super(card);
    }

    @Override
    public ScuzzbackScrounger copy() {
        return new ScuzzbackScrounger(this);
    }
}
