
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class GhituJourneymage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Wizard");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.WIZARD.getPredicate());
    }

    public GhituJourneymage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Ghitu Journeymage enters the battlefield, if you control another Wizard, Ghitu Journeymage deals 2 damage to each opponent.
        TriggeredAbility triggeredAbility = new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility,
                new PermanentsOnTheBattlefieldCondition(filter),
                "When {this} enters the battlefield, if you control another Wizard, {this} deals 2 damage to each opponent."
        ));
    }

    private GhituJourneymage(final GhituJourneymage card) {
        super(card);
    }

    @Override
    public GhituJourneymage copy() {
        return new GhituJourneymage(this);
    }
}
