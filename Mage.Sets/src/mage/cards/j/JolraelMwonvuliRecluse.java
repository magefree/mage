package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GreenCat2Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JolraelMwonvuliRecluse extends CardImpl {

    public JolraelMwonvuliRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you draw your second card each turn, create a 2/2 green Cat creature token.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new GreenCat2Token()), false, 2));

        // {4}{G}{G}: Until end of turn, creatures you control have base power and toughness X/X, where X is the number of cards in your hand.
        this.addAbility(new SimpleActivatedAbility(new SetBasePowerToughnessAllEffect(
                CardsInControllerHandCount.instance, CardsInControllerHandCount.instance,
                Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, true
        ).setText("until end of turn, creatures you control have base power and toughness X/X, " +
                "where X is the number of cards in your hand"), new ManaCostsImpl<>("{4}{G}{G}")));
    }

    private JolraelMwonvuliRecluse(final JolraelMwonvuliRecluse card) {
        super(card);
    }

    @Override
    public JolraelMwonvuliRecluse copy() {
        return new JolraelMwonvuliRecluse(this);
    }
}
