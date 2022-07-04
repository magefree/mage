
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.BatToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SkeletalVampire extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Bat");

    static {
        filter.add(SubType.BAT.getPredicate());
    }

    public SkeletalVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Skeletal Vampire enters the battlefield, create two 1/1 black Bat creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BatToken(), 2)));
        // {3}{B}{B}, Sacrifice a Bat: Create two 1/1 black Bat creature tokens with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new BatToken(), 2), new ManaCostsImpl<>("{3}{B}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        this.addAbility(ability);
        // Sacrifice a Bat: Regenerate Skeletal Vampire.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))));
    }

    private SkeletalVampire(final SkeletalVampire card) {
        super(card);
    }

    @Override
    public SkeletalVampire copy() {
        return new SkeletalVampire(this);
    }
}

