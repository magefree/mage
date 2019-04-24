
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public final class TendershootDryad extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Saprolings you control");

    static {
        filter.add(new SubtypePredicate(SubType.SAPROLING));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public TendershootDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ascend
        this.addAbility(new AscendAbility());

        // At the beginning of each upkeep, create a 1/1 green Saproling creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SaprolingToken()), TargetController.ANY, false));

        // Saprolings you control get +2/+2 as long as you have the city's blessing.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter),
                        CitysBlessingCondition.instance,
                        "Saprolings you control get +2/+2 as long as you have the city's blessing."
                )
        ));
    }

    public TendershootDryad(final TendershootDryad card) {
        super(card);
    }

    @Override
    public TendershootDryad copy() {
        return new TendershootDryad(this);
    }
}
