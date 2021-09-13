
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author nantuko
 */
public final class ThrabenSentry extends CardImpl {

    public ThrabenSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.secondSideCardClazz = ThrabenMilitia.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(VigilanceAbility.getInstance());

        // Whenever another creature you control dies, you may transform Thraben Sentry.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesCreatureTriggeredAbility(new TransformSourceEffect(), true, new FilterControlledCreaturePermanent()));
    }

    private ThrabenSentry(final ThrabenSentry card) {
        super(card);
    }

    @Override
    public ThrabenSentry copy() {
        return new ThrabenSentry(this);
    }
}
