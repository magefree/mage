
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WightOfPrecinctSix extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card in your opponents' graveyards");

    public WightOfPrecinctSix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Wight of Precinct Six gets +1/+1 for each creature card in your opponents' graveyards.
        DynamicValue boost = new CardsInOpponentGraveyardsCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(boost, boost, Duration.WhileOnBattlefield)));
    }

    private WightOfPrecinctSix(final WightOfPrecinctSix card) {
        super(card);
    }

    @Override
    public WightOfPrecinctSix copy() {
        return new WightOfPrecinctSix(this);
    }
}

class CardsInOpponentGraveyardsCount implements DynamicValue {

    private FilterCard filter;

    public CardsInOpponentGraveyardsCount() {
       this(new FilterCard());
    }

    public CardsInOpponentGraveyardsCount(FilterCard filter) {
       this.filter = filter;
    }

    private CardsInOpponentGraveyardsCount(CardsInOpponentGraveyardsCount dynamicValue) {
       this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
       int amount = 0;
       for (UUID playerUUID : game.getOpponents(sourceAbility.getControllerId())) {
           Player player = game.getPlayer(playerUUID);
           if (player != null) {
               amount += player.getGraveyard().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
           }
       }
       return amount;
    }

    @Override
    public DynamicValue copy() {
       return new CardsInOpponentGraveyardsCount(this);
    }

    @Override
    public String toString() {
       return "1";
    }

    @Override
    public String getMessage() {
       return filter.getMessage();
    }
}
