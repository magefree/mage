
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class CetaDisciple extends CardImpl {

    public CetaDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R));
        firstAbility.addCost(new TapSourceCost());
        firstAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(firstAbility);
        Ability secondAbility = new AnyColorManaAbility(new ColoredManaCost(ColoredManaSymbol.G));
        secondAbility.addCost(new TapSourceCost());
        this.addAbility(secondAbility);
    }

    private CetaDisciple(final CetaDisciple card) {
        super(card);
    }

    @Override
    public CetaDisciple copy() {
        return new CetaDisciple(this);
    }
}
