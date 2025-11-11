package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BitterWork extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public BitterWork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");

        // Whenever you attack a player with one or more creatures with power 4 or greater, draw a card.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, SetTargetPointer.NONE
        ).setTriggerPhrase("Whenever you attack a player with one or more creatures with power 4 or greater, "));

        // Exhaust -- {4}: Earthbend 4. Activate only during your turn.
        Ability ability = new ExhaustAbility(new EarthbendTargetEffect(4), new GenericManaCost(4))
                .setCondition(MyTurnCondition.instance);
        ability.addTarget(new TargetControlledLandPermanent());
        ability.addEffect(new InfoEffect("Activate only during your turn"));
        this.addAbility(ability);
    }

    private BitterWork(final BitterWork card) {
        super(card);
    }

    @Override
    public BitterWork copy() {
        return new BitterWork(this);
    }
}
