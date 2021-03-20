
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author TheElk801
 */
public final class DreamcallerSiren extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DreamcallerSiren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Dreamcaller Siren can only block creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());

        // When Dreamcaller Siren enters the battlefield, if you control another Pirate, tap up to two nonland permanents.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetNonlandPermanent(0, 2, false));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability,
                new PermanentsOnTheBattlefieldCondition(filter),
                "when {this} enters the battlefield, if you control another Pirate, tap up to two target nonland permanents."));
    }

    private DreamcallerSiren(final DreamcallerSiren card) {
        super(card);
    }

    @Override
    public DreamcallerSiren copy() {
        return new DreamcallerSiren(this);
    }
}
