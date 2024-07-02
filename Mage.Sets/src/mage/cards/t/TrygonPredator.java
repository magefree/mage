
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class TrygonPredator extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment that player controls");

    public TrygonPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Trygon Predator deals combat damage to a player, you may destroy target artifact or enchantment that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), true, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private TrygonPredator(final TrygonPredator card) {
        super(card);
    }

    @Override
    public TrygonPredator copy() {
        return new TrygonPredator(this);
    }
}
