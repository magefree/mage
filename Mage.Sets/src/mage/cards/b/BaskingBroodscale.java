package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaskingBroodscale extends CardImpl {

    public BaskingBroodscale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {1}{G}: Adapt 1.
        this.addAbility(new AdaptAbility(1, "{1}{G}"));

        // Whenever one or more +1/+1 counters are put on Basking Broodscale, you may create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(
                new CreateTokenEffect(new EldraziSpawnToken()), true
        ));
    }

    private BaskingBroodscale(final BaskingBroodscale card) {
        super(card);
    }

    @Override
    public BaskingBroodscale copy() {
        return new BaskingBroodscale(this);
    }
}
