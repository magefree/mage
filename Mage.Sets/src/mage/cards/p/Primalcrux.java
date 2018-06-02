
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class Primalcrux extends CardImpl {

    public Primalcrux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Chroma - Primalcrux's power and toughness are each equal to the number of green mana symbols in the mana costs of permanents you control.
        Effect effect = new SetPowerToughnessSourceEffect(new ChromaPrimalcruxCount(), Duration.WhileOnBattlefield);
        effect.setText("<i>Chroma</i> &mdash; Primalcrux's power and toughness are each equal to the number of green mana symbols in the mana costs of permanents you control.");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    public Primalcrux(final Primalcrux card) {
        super(card);
    }

    @Override
    public Primalcrux copy() {
        return new Primalcrux(this);
    }
}

class ChromaPrimalcruxCount implements DynamicValue {

    private int chroma;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        chroma = 0;
        for (Card card : game.getBattlefield().getAllActivePermanents(new FilterControlledPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += card.getManaCost().getMana().getGreen();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaPrimalcruxCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
