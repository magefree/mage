
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoatToken;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class SpringjackShepherd extends CardImpl {

    public SpringjackShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Chroma - When Springjack Shepherd enters the battlefield, create a 0/1 white Goat creature token for each white mana symbol in the mana costs of permanents you control.
        Effect effect = new CreateTokenEffect(new GoatToken(), new ChromaSpringjackShepherdCount());
        effect.setText("<i>Chroma</i> &mdash; When Springjack Shepherd enters the battlefield, create a 0/1 white Goat creature token for each white mana symbol in the mana costs of permanents you control.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false, true));

    }

    public SpringjackShepherd(final SpringjackShepherd card) {
        super(card);
    }

    @Override
    public SpringjackShepherd copy() {
        return new SpringjackShepherd(this);
    }
}

class ChromaSpringjackShepherdCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int chroma = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += permanent.getManaCost().getMana().getWhite();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaSpringjackShepherdCount();
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
