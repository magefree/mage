package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class GrandNagusZek extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE);

    public GrandNagusZek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FERENGI);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Grand Nagus Zek gets +1/+1 for each Treasure you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter),
            new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));

        // Whenever another creature dies, create a Treasure token. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false, true
        ).setTriggersLimitEachTurn(1));
    }

    private GrandNagusZek(final GrandNagusZek card) {
        super(card);
    }

    @Override
    public GrandNagusZek copy() {
        return new GrandNagusZek(this);
    }
}
