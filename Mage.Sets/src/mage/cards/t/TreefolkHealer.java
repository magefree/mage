
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class TreefolkHealer extends CardImpl {

    public TreefolkHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{W}, {tap}: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2),
                new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TreefolkHealer(final TreefolkHealer card) {
        super(card);
    }

    @Override
    public TreefolkHealer copy() {
        return new TreefolkHealer(this);
    }
}
