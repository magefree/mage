package mage.cards.u;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UginsBinding extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");
    private static final FilterSpell filter2 = new FilterSpell("a colorless spell with mana value 7 or greater");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter2.add(ColorlessPredicate.instance);
        filter2.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 6));
    }

    public UginsBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Return target nonland permanent you don't control to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Whenever you cast a colorless spell with mana value 7 or greater, you may exile Ugin's Binding from your graveyard. When you do, return each nonland permanent you don't control to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD,
                new DoWhenCostPaid(
                        new ReflexiveTriggeredAbility(
                                new ReturnToHandFromBattlefieldAllEffect(filter)
                                        .setText("return each nonland permanent you don't control to its owner's hand"),
                                false
                        ), new ExileSourceFromGraveCost(), "Exile this from your graveyard?"
                ), filter2, false, SetTargetPointer.NONE
        ));
    }

    private UginsBinding(final UginsBinding card) {
        super(card);
    }

    @Override
    public UginsBinding copy() {
        return new UginsBinding(this);
    }
}
