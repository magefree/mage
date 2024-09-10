
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.SliverToken;

/**
 *
 * @author cbt33
 */
public final class SliverQueen extends CardImpl {

    public SliverQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // {2}: Create a 1/1 colorless Sliver creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SliverToken()), new ManaCostsImpl<>("{2}")));
    }

    private SliverQueen(final SliverQueen card) {
        super(card);
    }

    @Override
    public SliverQueen copy() {
        return new SliverQueen(this);
    }
}
