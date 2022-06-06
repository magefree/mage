
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX2
 */
public final class GraverobberSpider extends CardImpl {

    public GraverobberSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {3}{B}: Graverobber Spider gets +X/+X until end of turn, where X is the number of creature cards in your graveyard. Activate this ability only once each turn.
        DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards"));
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true), new ManaCostsImpl<>("{3}{B}"));
        this.addAbility(ability);
    }

    private GraverobberSpider(final GraverobberSpider card) {
        super(card);
    }

    @Override
    public GraverobberSpider copy() {
        return new GraverobberSpider(this);
    }
}
