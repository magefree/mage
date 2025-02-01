package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ExhaustAbilityPredicate;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangersAetherhive extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("an exhaust ability");

    static {
        filter.add(ExhaustAbilityPredicate.instance);
    }

    public RangersAetherhive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you activate an exhaust ability, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new ActivateAbilityTriggeredAbility(
                new CreateTokenEffect(new ThopterColorlessToken()), filter, SetTargetPointer.NONE
        ));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private RangersAetherhive(final RangersAetherhive card) {
        super(card);
    }

    @Override
    public RangersAetherhive copy() {
        return new RangersAetherhive(this);
    }
}
