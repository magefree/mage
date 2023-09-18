
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class BloodfireMentor extends CardImpl {

    public BloodfireMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.EFREET, SubType.SHAMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // {2}{U}, T: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1,1), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private BloodfireMentor(final BloodfireMentor card) {
        super(card);
    }

    @Override
    public BloodfireMentor copy() {
        return new BloodfireMentor(this);
    }
}
