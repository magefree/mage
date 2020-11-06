package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TroveTracker extends CardImpl {

    public TroveTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Trove Tracker dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Encore {5}{U}{U}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{5}{U}{U}")));
    }

    private TroveTracker(final TroveTracker card) {
        super(card);
    }

    @Override
    public TroveTracker copy() {
        return new TroveTracker(this);
    }
}
