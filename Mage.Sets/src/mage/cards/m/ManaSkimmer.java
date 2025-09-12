
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author nigelzor
 */
public final class ManaSkimmer extends CardImpl {

    public ManaSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Mana Skimmer deals damage to a player, tap target land that player controls. That land doesn't untap during its controller's next untap step.
        Ability ability = new DealsDamageToAPlayerTriggeredAbility(new TapTargetEffect(), false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That land"));
        ability.addTarget(new TargetLandPermanent().withTargetName("target land that player controls"));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private ManaSkimmer(final ManaSkimmer card) {
        super(card);
    }

    @Override
    public ManaSkimmer copy() {
        return new ManaSkimmer(this);
    }
}
