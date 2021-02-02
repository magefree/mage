
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author Wehk
 */
public final class GoldmeadowDodger extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creatures with power 4 or greater");

    static {
        FILTER.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public GoldmeadowDodger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Goldmeadow Dodger can't be blocked by creatures with power 4 or greater.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(FILTER, Duration.WhileOnBattlefield)));
    }

    private GoldmeadowDodger(final GoldmeadowDodger card) {
        super(card);
    }

    @Override
    public GoldmeadowDodger copy() {
        return new GoldmeadowDodger(this);
    }
}
