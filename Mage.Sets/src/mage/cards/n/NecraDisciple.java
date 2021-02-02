
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class NecraDisciple extends CardImpl {

    public NecraDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability firstAbility = new AnyColorManaAbility(new ColoredManaCost(ColoredManaSymbol.G));
        firstAbility.addCost(new TapSourceCost());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new ColoredManaCost(ColoredManaSymbol.W));
        secondAbility.addCost(new TapSourceCost());
        secondAbility.addTarget(new TargetAnyTarget());
        this.addAbility(secondAbility);
    }

    private NecraDisciple(final NecraDisciple card) {
        super(card);
    }

    @Override
    public NecraDisciple copy() {
        return new NecraDisciple(this);
    }
}
