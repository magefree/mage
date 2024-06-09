
package mage.cards.k;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KozileksReturn extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Eldrazi creature spell with mana value 7 or greater");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 6));
    }

    public KozileksReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Kozilek's Return deals 2 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, new FilterCreaturePermanent()));

        // Whenever you cast an Eldrazi creature spell with converted mana cost 7 or greater, you may exile Kozilek's Return from your graveyard.
        // If you do, Kozilek's Return deals 5 damage to each creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new DamageAllEffect(5, new FilterCreaturePermanent()),
                        new ExileSourceFromGraveCost()
                ),
                filter, false, SetTargetPointer.NONE
        ));
    }

    private KozileksReturn(final KozileksReturn card) {
        super(card);
    }

    @Override
    public KozileksReturn copy() {
        return new KozileksReturn(this);
    }
}
