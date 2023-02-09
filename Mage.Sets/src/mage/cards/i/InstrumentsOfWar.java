package mage.cards.i;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author Grath
 */
public final class InstrumentsOfWar extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public InstrumentsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // As Instruments of War enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));
    }

    private InstrumentsOfWar(final InstrumentsOfWar card) {
        super(card);
    }

    @Override
    public InstrumentsOfWar copy() {
        return new InstrumentsOfWar(this);
    }
}
