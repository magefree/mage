package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiantKoi extends CardImpl {

    public GiantKoi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Waterbend {3}: This creature can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new WaterbendCost(3)
        ));

        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private GiantKoi(final GiantKoi card) {
        super(card);
    }

    @Override
    public GiantKoi copy() {
        return new GiantKoi(this);
    }
}
