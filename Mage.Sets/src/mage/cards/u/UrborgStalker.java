package mage.cards.u;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrborgStalker extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("that player controls a nonblack, nonland permanent");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public UrborgStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of each player's upkeep, if that player controls a nonblack, nonland permanent, Urborg Stalker deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new DamageTargetEffect(1, true, "that player"), false
        ).withInterveningIf(condition));
    }

    private UrborgStalker(final UrborgStalker card) {
        super(card);
    }

    @Override
    public UrborgStalker copy() {
        return new UrborgStalker(this);
    }
}
