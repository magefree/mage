
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class LatullaKeldonOverseer extends CardImpl {

    public LatullaKeldonOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{R}, {tap}, Discard two cards: Latulla, Keldon Overseer deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, 2, new FilterCard("two cards"))));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LatullaKeldonOverseer(final LatullaKeldonOverseer card) {
        super(card);
    }

    @Override
    public LatullaKeldonOverseer copy() {
        return new LatullaKeldonOverseer(this);
    }
}
