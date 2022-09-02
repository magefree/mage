
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class MasterOfEtherium extends CardImpl {

    private static final FilterControlledPermanent filterCounted = new FilterControlledPermanent("artifacts you control");
    private static final FilterCreaturePermanent filterBoosted = new FilterCreaturePermanent("artifact creatures");

    static {
        filterCounted.add(CardType.ARTIFACT.getPredicate());
        filterBoosted.add(CardType.ARTIFACT.getPredicate());
        filterBoosted.add(TargetController.YOU.getControllerPredicate());
    }

    public MasterOfEtherium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Master of Etherium's power and toughness are each equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterCounted), Duration.EndOfGame)));

        // Other artifact creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBoosted, true)));
    }

    private MasterOfEtherium(final MasterOfEtherium card) {
        super(card);
    }

    @Override
    public MasterOfEtherium copy() {
        return new MasterOfEtherium(this);
    }
}
