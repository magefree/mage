
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class CacklingWitch extends CardImpl {

    public CacklingWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{B}, {tap}, Discard a card: Target creature gets +X/+0 until end of turn.
        ManacostVariableValue manaX = ManacostVariableValue.REGULAR;
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(manaX, StaticValue.get(0), Duration.EndOfTurn),
                new ManaCostsImpl<>("{X}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CacklingWitch(final CacklingWitch card) {
        super(card);
    }

    @Override
    public CacklingWitch copy() {
        return new CacklingWitch(this);
    }
}
