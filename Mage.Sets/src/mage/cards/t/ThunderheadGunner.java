package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderheadGunner extends CardImpl {

    public ThunderheadGunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Discard a card: Draw a card. Activate only as a sorcery and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ).setTiming(TimingRule.SORCERY));
    }

    private ThunderheadGunner(final ThunderheadGunner card) {
        super(card);
    }

    @Override
    public ThunderheadGunner copy() {
        return new ThunderheadGunner(this);
    }
}
