 
package mage.cards.g;

 import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 * @author Loki
 */
public final class GruulWarPlow extends CardImpl {

    public GruulWarPlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));

        // {1}{R}{G}: Gruul War Plow becomes a 4/4 Juggernaut artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GruulWarPlowToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}{G}")));
    }

    private GruulWarPlow(final GruulWarPlow card) {
        super(card);
    }

    @Override
    public GruulWarPlow copy() {
        return new GruulWarPlow(this);
    }
}

class GruulWarPlowToken extends TokenImpl {

    GruulWarPlowToken() {
        super("Juggernaut", "4/4 Juggernaut artifact creature");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.JUGGERNAUT);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
    public GruulWarPlowToken(final GruulWarPlowToken token) {
        super(token);
    }

    public GruulWarPlowToken copy() {
        return new GruulWarPlowToken(this);
    }
}
