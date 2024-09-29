package mage.cards.r;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiftburstHellion extends CardImpl {

    public RiftburstHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Disguise {4}{R/G}{R/G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{R/G}{R/G}")));
    }

    private RiftburstHellion(final RiftburstHellion card) {
        super(card);
    }

    @Override
    public RiftburstHellion copy() {
        return new RiftburstHellion(this);
    }
}
