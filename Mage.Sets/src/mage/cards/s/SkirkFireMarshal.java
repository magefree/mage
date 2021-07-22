
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SkirkFireMarshal extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Goblins you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public SkirkFireMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));

        // Tap five untapped Goblins you control: Skirk Fire Marshal deals 10 damage to each creature and each player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageEverythingEffect(10),
                new TapTargetCost(new TargetControlledCreaturePermanent(5,5, filter, true)));
        this.addAbility(ability);
    }

    private SkirkFireMarshal(final SkirkFireMarshal card) {
        super(card);
    }

    @Override
    public SkirkFireMarshal copy() {
        return new SkirkFireMarshal(this);
    }
}
