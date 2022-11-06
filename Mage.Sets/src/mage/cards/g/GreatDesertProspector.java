package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.PowerstoneToken;

/**
 *
 * @author weirddan455
 */
public final class GreatDesertProspector extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);

    public GreatDesertProspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Great Desert Prospector enters the battlefield, create a tapped Powerstone token for each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PowerstoneToken(), count, true, false)));
    }

    private GreatDesertProspector(final GreatDesertProspector card) {
        super(card);
    }

    @Override
    public GreatDesertProspector copy() {
        return new GreatDesertProspector(this);
    }
}
