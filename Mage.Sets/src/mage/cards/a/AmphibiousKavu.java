
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author L_J
 */
public final class AmphibiousKavu extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue and/or black creatures");
    
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.BLACK)));
    }

    public AmphibiousKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Amphibious Kavu blocks or becomes blocked by one or more blue and/or black creatures, Amphibious Kavu gets +3/+3 until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(new BoostSourceEffect(3, 3, Duration.EndOfTurn), filter, false));
    }

    private AmphibiousKavu(final AmphibiousKavu card) {
        super(card);
    }

    @Override
    public AmphibiousKavu copy() {
        return new AmphibiousKavu(this);
    }
}
