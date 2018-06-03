
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class StensiaInnkeeper extends CardImpl {

    private final static FilterLandPermanent filter = new FilterLandPermanent("land an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public StensiaInnkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stensia Innkeeper enters the battlefield, tap target land an opponent controls. That land doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That land"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public StensiaInnkeeper(final StensiaInnkeeper card) {
        super(card);
    }

    @Override
    public StensiaInnkeeper copy() {
        return new StensiaInnkeeper(this);
    }
}
