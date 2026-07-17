package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StockmanMadFlyEntist extends CardImpl {

    public StockmanMadFlyEntist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Stockman enters, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private StockmanMadFlyEntist(final StockmanMadFlyEntist card) {
        super(card);
    }

    @Override
    public StockmanMadFlyEntist copy() {
        return new StockmanMadFlyEntist(this);
    }
}
