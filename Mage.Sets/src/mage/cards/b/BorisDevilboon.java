
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.MinorDemonToken;

/**
 *
 * @author LoneFox
 */
public final class BorisDevilboon extends CardImpl {

    public BorisDevilboon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{B}{R}, {tap}: Create a 1/1 black and red Demon creature token named Minor Demon.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new MinorDemonToken()), new ManaCostsImpl<>("{2}{B}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BorisDevilboon(final BorisDevilboon card) {
        super(card);
    }

    @Override
    public BorisDevilboon copy() {
        return new BorisDevilboon(this);
    }
}
