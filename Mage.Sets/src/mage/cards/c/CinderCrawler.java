package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.SourceBlockedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class CinderCrawler extends CardImpl {

    public CinderCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}: Cinder Crawler gets +1/+0 until end of turn. Activate this ability only if Cinder Crawler is blocked.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}"),
                SourceBlockedCondition.instance
        ));
    }

    private CinderCrawler(final CinderCrawler card) {
        super(card);
    }

    @Override
    public CinderCrawler copy() {
        return new CinderCrawler(this);
    }
}
