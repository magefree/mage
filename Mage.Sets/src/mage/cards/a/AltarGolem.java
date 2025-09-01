
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AltarGolem extends CardImpl {

    public AltarGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Altar Golem's power and toughness are each equal to the number of creatures on the battlefield.
        DynamicValue amount = new PermanentsOnBattlefieldCount(new FilterCreaturePermanent("creatures on the battlefield"));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(amount)));

        // Altar Golem doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // Tap five untapped creatures you control: Untap Altar Golem.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new TapTargetCost(new TargetControlledPermanent(5, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES))));

    }

    private AltarGolem(final AltarGolem card) {
        super(card);
    }

    @Override
    public AltarGolem copy() {
        return new AltarGolem(this);
    }
}
