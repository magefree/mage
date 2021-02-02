
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;

/**
 *
 * @author TheElk801
 */
public final class KjeldoranFrostbeast extends CardImpl {

    public KjeldoranFrostbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures blocking or blocked by it");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                new BlockingAttackerIdPredicate(this.getId())));
        // At end of combat, destroy all creatures blocking or blocked by Kjeldoran Frostbeast.
        Ability ability = new EndOfCombatTriggeredAbility(new DestroyAllEffect(filter, false), false);
        this.addAbility(ability);
    }

    private KjeldoranFrostbeast(final KjeldoranFrostbeast card) {
        super(card);
    }

    @Override
    public KjeldoranFrostbeast copy() {
        return new KjeldoranFrostbeast(this);
    }
}
