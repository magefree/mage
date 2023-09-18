package mage.cards.d;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class DrownedCatacomb extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.ISLAND.getPredicate()
        ));
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public DrownedCatacomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        this.addAbility(new EntersBattlefieldAbility(
                new TapSourceEffect(), condition, null,
                "tapped unless you control an Island or a Swamp"
        ));
        this.addAbility(new BlackManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private DrownedCatacomb(final DrownedCatacomb card) {
        super(card);
    }

    @Override
    public DrownedCatacomb copy() {
        return new DrownedCatacomb(this);
    }
}
