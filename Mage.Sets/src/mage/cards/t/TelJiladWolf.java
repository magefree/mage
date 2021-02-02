
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author wetterlicht
 */
public final class TelJiladWolf extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public TelJiladWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Tel-Jilad Wolf becomes blocked by an artifact creature, Tel-Jilad Wolf gets +3/+3 until end of turn.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostSourceEffect(3, 3, Duration.EndOfTurn), filter, false));
    }

    private TelJiladWolf(final TelJiladWolf card) {
        super(card);
    }

    @Override
    public TelJiladWolf copy() {
        return new TelJiladWolf(this);
    }
}
