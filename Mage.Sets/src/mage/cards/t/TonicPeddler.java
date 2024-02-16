
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Derpthemeus
 */
public final class TonicPeddler extends CardImpl {

    public TonicPeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, {tap}, Discard a card: Target player gains 3 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeTargetEffect(3), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private TonicPeddler(final TonicPeddler card) {
        super(card);
    }

    @Override
    public TonicPeddler copy() {
        return new TonicPeddler(this);
    }
}
