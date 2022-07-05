
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.KelpToken;

/**
 *
 * @author fireshoes
 */
public final class WallOfKelp extends CardImpl {

    public WallOfKelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {U}{U}, {tap}: Create a 0/1 blue Plant Wall creature token with defender named Kelp.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new KelpToken()), new ManaCostsImpl<>("{U}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WallOfKelp(final WallOfKelp card) {
        super(card);
    }

    @Override
    public WallOfKelp copy() {
        return new WallOfKelp(this);
    }
}
