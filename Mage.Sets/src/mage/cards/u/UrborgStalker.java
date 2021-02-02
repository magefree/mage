
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class UrborgStalker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public UrborgStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of each player's upkeep, if that player controls a nonblack, nonland permanent, Urborg Stalker deals 1 damage to that player.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(1), TargetController.ANY, false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "At the beginning of each player's upkeep, "
                + "if that player controls a nonblack, nonland permanent, "
                + "{this} deals 1 damage to that player."
        ));
    }

    private UrborgStalker(final UrborgStalker card) {
        super(card);
    }

    @Override
    public UrborgStalker copy() {
        return new UrborgStalker(this);
    }
}
