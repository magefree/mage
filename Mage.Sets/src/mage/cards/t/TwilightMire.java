

package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class TwilightMire extends CardImpl {

    public TwilightMire (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        
        // {R/W}, {tap}: Add {R}{R}, {R}{W}, or {W}{W}.
        this.addAbility(new ColorlessManaAbility());
        
        // {B/G}, {tap}: Add {B}{B}, {B}{G}, or {G}{G}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new ManaCostsImpl<>("{B/G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 0, 1, 0, 0, 0), new ManaCostsImpl<>("{B/G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new ManaCostsImpl<>("{B/G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);        }

    public TwilightMire (final TwilightMire card) {
        super(card);
    }

    @Override
    public TwilightMire copy() {
        return new TwilightMire(this);
    }

}
