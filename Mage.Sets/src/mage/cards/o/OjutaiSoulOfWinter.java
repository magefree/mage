
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class OjutaiSoulOfWinter extends CardImpl {

    private static final FilterCreaturePermanent filterDragon = new FilterCreaturePermanent("Dragon you control");
    private static final FilterPermanent filterNonlandPermanent = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filterDragon.add(SubType.DRAGON.getPredicate());
        filterDragon.add(TargetController.YOU.getControllerPredicate());
        filterNonlandPermanent.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public OjutaiSoulOfWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Whenever a Dragon you control attacks, tap target nonland permanent an opponent controls. That permanent doesn't untap during its controller's next untap step.
        Ability ability = new AttacksAllTriggeredAbility(
                new TapTargetEffect(),
                false, filterDragon, SetTargetPointer.NONE, false);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That permanent"));
        ability.addTarget(new TargetPermanent(filterNonlandPermanent));
        this.addAbility(ability);
    }

    private OjutaiSoulOfWinter(final OjutaiSoulOfWinter card) {
        super(card);
    }

    @Override
    public OjutaiSoulOfWinter copy() {
        return new OjutaiSoulOfWinter(this);
    }
}
