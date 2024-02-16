

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AntQueen extends CardImpl {

    private static InsectToken insectToken = new InsectToken();

    public AntQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.INSECT);


        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(insectToken), new ManaCostsImpl<>("{1}{G}")));
    }

    private AntQueen(final AntQueen card) {
        super(card);
    }

    @Override
    public AntQueen copy() {
        return new AntQueen(this);
    }

}
