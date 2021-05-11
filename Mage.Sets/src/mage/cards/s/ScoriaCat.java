
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LoneFox
 */
public final class ScoriaCat extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ScoriaCat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Scoria Cat gets +3/+3 as long as you control no untapped lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
            new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
            "{this} gets +3/+3 as long as you control no untapped lands")));
    }

    private ScoriaCat(final ScoriaCat card) {
        super(card);
    }

    @Override
    public ScoriaCat copy() {
        return new ScoriaCat(this);
    }
}
