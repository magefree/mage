
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamcallerSiren extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PIRATE, "you control another Pirate");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

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
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect()).withInterveningIf(condition);
        ability.addTarget(new TargetNonlandPermanent(0, 2, false));
        this.addAbility(ability);
    }

    private DreamcallerSiren(final DreamcallerSiren card) {
        super(card);
    }

    @Override
    public DreamcallerSiren copy() {
        return new DreamcallerSiren(this);
    }
}
