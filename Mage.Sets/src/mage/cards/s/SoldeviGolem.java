
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SoldeviGolem extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(TappedPredicate.TAPPED);
    }

    public SoldeviGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Soldevi Golem doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // At the beginning of your upkeep, you may untap target tapped creature an opponent controls. If you do, untap Soldevi Golem.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new UntapTargetEffect().setText("untap target tapped creature an opponent controls"), TargetController.YOU, true);
        ability.addEffect(new UntapSourceEffect().setText("If you do, untap {this}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SoldeviGolem(final SoldeviGolem card) {
        super(card);
    }

    @Override
    public SoldeviGolem copy() {
        return new SoldeviGolem(this);
    }
}
