
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.BlockedCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Markedagain
 */
public final class BerserkMurlodont extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Beast");

    static {
        filter.add(new SubtypePredicate(SubType.BEAST));
    }

    public BerserkMurlodont(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Beast becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        BlockedCreatureCount value = new BlockedCreatureCount();
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +1/+1 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, filter, false));
    }

    public BerserkMurlodont(final BerserkMurlodont card) {
        super(card);
    }

    @Override
    public BerserkMurlodont copy() {
        return new BerserkMurlodont(this);
    }
}
