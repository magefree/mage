
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class AkkiBlizzardHerder extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("land");

    public AkkiBlizzardHerder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Akki Blizzard-Herder dies, each player sacrifices a land.
        this.addAbility(new DiesSourceTriggeredAbility(new SacrificeAllEffect(filter)));
    }

    private AkkiBlizzardHerder(final AkkiBlizzardHerder card) {
        super(card);
    }

    @Override
    public AkkiBlizzardHerder copy() {
        return new AkkiBlizzardHerder(this);
    }
}
