package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KjeldoranFrostbeast extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creatures blocking or blocked by it");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public KjeldoranFrostbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At end of combat, destroy all creatures blocking or blocked by Kjeldoran Frostbeast.
        this.addAbility(new EndOfCombatTriggeredAbility(new DestroyAllEffect(filter, false), false));
    }

    private KjeldoranFrostbeast(final KjeldoranFrostbeast card) {
        super(card);
    }

    @Override
    public KjeldoranFrostbeast copy() {
        return new KjeldoranFrostbeast(this);
    }
}
