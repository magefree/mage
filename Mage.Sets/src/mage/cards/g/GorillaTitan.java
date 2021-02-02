
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class GorillaTitan extends CardImpl {

    public GorillaTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Gorilla Titan gets +4/+4 as long as there are no cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(4,4, Duration.WhileInGraveyard),
                new GorillaTitanCondition(),
                "{this} gets +4/+4 as long as there are no cards in your graveyard"
                )));
    }

    private GorillaTitan(final GorillaTitan card) {
        super(card);
    }

    @Override
    public GorillaTitan copy() {
        return new GorillaTitan(this);
    }
}

class GorillaTitanCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null)
        {
                    return p.getGraveyard().isEmpty();
        }
        return false;
    }
}
