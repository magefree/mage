package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrawlingCrawler extends CardImpl {

    public ScrawlingCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, each player draws a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardAllEffect(1)));

        // Whenever an opponent draws a card, that player loses 1 life.
        this.addAbility(new DrawCardOpponentTriggeredAbility(
                new LoseLifeTargetEffect(1), false, true
        ));
    }

    private ScrawlingCrawler(final ScrawlingCrawler card) {
        super(card);
    }

    @Override
    public ScrawlingCrawler copy() {
        return new ScrawlingCrawler(this);
    }
}
