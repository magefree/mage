
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public final class RoyalHerbalist extends CardImpl {

    public RoyalHerbalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, Exile the top card of your library: You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addCost(new ExileFromTopOfLibraryCost(1));
        this.addAbility(ability);
    }

    private RoyalHerbalist(final RoyalHerbalist card) {
        super(card);
    }

    @Override
    public RoyalHerbalist copy() {
        return new RoyalHerbalist(this);
    }
}
