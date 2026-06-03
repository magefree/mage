package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class KillmongerScourgeOfWakanda extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls");
    private static final Condition condition = new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_CREATURE);

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KillmongerScourgeOfWakanda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Killmonger enters, you may sacrifice another creature. When you do, destroy target nonland permanent an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
            new DestroyTargetEffect(),
            new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE)
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // As long as there are two or more creature cards in your graveyard, Killmonger gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield),
            condition,
            "as long as there are two or more creature cards in your graveyard, {this} gets +2/+1"
        )));
    }

    private KillmongerScourgeOfWakanda(final KillmongerScourgeOfWakanda card) {
        super(card);
    }

    @Override
    public KillmongerScourgeOfWakanda copy() {
        return new KillmongerScourgeOfWakanda(this);
    }
}
