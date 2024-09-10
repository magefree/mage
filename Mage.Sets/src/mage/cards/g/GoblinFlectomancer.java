
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox

 */
public final class GoblinFlectomancer extends CardImpl {

    public GoblinFlectomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Goblin Flectomancer: You may change the targets of target instant or sorcery spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChooseNewTargetsTargetEffect().setText("you may change the targets of target instant or sorcery spell"), new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(new FilterInstantOrSorcerySpell()));
        this.addAbility(ability);
    }

    private GoblinFlectomancer(final GoblinFlectomancer card) {
        super(card);
    }

    @Override
    public GoblinFlectomancer copy() {
        return new GoblinFlectomancer(this);
    }
}
