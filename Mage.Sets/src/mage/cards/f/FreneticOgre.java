
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class FreneticOgre extends CardImpl {

    public FreneticOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {R}, Discard a card at random: Frenetic Ogre gets +3/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addCost(new DiscardCardCost(true));
        this.addAbility(ability);
    }

    private FreneticOgre(final FreneticOgre card) {
        super(card);
    }

    @Override
    public FreneticOgre copy() {
        return new FreneticOgre(this);
    }
}
