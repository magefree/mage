package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BoomBox extends CardImpl {

    public BoomBox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {6}, {T}, Sacrifice Boom Box: Destroy up to one target artifact, up to one target creature, and up to one target land.
        Ability ability = new SimpleActivatedAbility(
                new DestroyTargetEffect("")
                        .setText("Destroy up to one target artifact, up to one target creature, and up to one target land")
                        .setTargetPointer(new EachTargetPointer()),
                new GenericManaCost(6)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent(0, 1));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addTarget(new TargetLandPermanent(0, 1));
        this.addAbility(ability);
    }

    private BoomBox(final BoomBox card) {
        super(card);
    }

    @Override
    public BoomBox copy() {
        return new BoomBox(this);
    }
}
