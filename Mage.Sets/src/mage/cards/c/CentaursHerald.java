
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.CentaurToken;

/**
 *
 * @author LevelX2
 */
public final class CentaursHerald extends CardImpl {

    public CentaursHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {2}{G}, Sacrifice Centaur's Herald: Create a 3/3 green Centaur creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new CentaurToken()), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CentaursHerald(final CentaursHerald card) {
        super(card);
    }

    @Override
    public CentaursHerald copy() {
        return new CentaursHerald(this);
    }
}
