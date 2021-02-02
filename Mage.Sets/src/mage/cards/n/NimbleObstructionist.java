
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;

/**
 *
 * @author caldover
 */
public final class NimbleObstructionist extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated or triggered ability you don't control");
    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public NimbleObstructionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{U}")));

        // When you cycle Nimble Obstructionist, counter target activated or triggered ability you don't control.
        Ability ability = new CycleTriggeredAbility(new CounterTargetEffect());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);
    }

    private NimbleObstructionist(final NimbleObstructionist card) {
        super(card);
    }

    @Override
    public NimbleObstructionist copy() {
        return new NimbleObstructionist(this);
    }
}
