
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinRogueToken;

/**
 *
 * @author fireshoes
 */
public final class BoggartMob extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Goblin you control");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public BoggartMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Champion a Goblin
        this.addAbility(new ChampionAbility(this, SubType.GOBLIN));

        // Whenever a Goblin you control deals combat damage to a player, you may create a 1/1 black Goblin Rogue creature token.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new GoblinRogueToken()),
                filter, true, SetTargetPointer.NONE, true));
    }

    private BoggartMob(final BoggartMob card) {
        super(card);
    }

    @Override
    public BoggartMob copy() {
        return new BoggartMob(this);
    }
}
