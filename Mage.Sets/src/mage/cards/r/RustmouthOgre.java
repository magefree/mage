
package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class RustmouthOgre extends CardImpl {

    private static final FilterArtifactPermanent filter
            = new FilterArtifactPermanent("artifact that player controls");

    public RustmouthOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Rustmouth Ogre deals combat damage to a player, you may destroy target artifact that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), true, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private RustmouthOgre(final RustmouthOgre card) {
        super(card);
    }

    @Override
    public RustmouthOgre copy() {
        return new RustmouthOgre(this);
    }
}
