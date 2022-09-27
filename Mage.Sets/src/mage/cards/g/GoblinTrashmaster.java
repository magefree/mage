package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinTrashmaster extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.GOBLIN, "Goblins");
    private static final FilterControlledCreaturePermanent filter2
            = new FilterControlledCreaturePermanent(SubType.GOBLIN, "a Goblin");

    public GoblinTrashmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Goblins you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(
                        1, 1, Duration.WhileOnBattlefield,
                        filter, true
                )
        ));

        // Sacrifice a Goblin: Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(
                new DestroyTargetEffect(),
                new SacrificeTargetCost(filter)
        );
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private GoblinTrashmaster(final GoblinTrashmaster card) {
        super(card);
    }

    @Override
    public GoblinTrashmaster copy() {
        return new GoblinTrashmaster(this);
    }
}
