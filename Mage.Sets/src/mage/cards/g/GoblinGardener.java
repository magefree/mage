
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox

 */
public final class GoblinGardener extends CardImpl {

    public GoblinGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Goblin Gardener dies, destroy target land.
        Ability ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GoblinGardener(final GoblinGardener card) {
        super(card);
    }

    @Override
    public GoblinGardener copy() {
        return new GoblinGardener(this);
    }
}
