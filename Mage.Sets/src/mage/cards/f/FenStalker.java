
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
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
public final class FenStalker extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public FenStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Fen Stalker has fear as long as you control no untapped lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new GainAbilitySourceEffect(FearAbility.getInstance(),
            Duration.WhileOnBattlefield), new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
            "{this} has fear as long as you control no untapped lands")));
    }

    private FenStalker(final FenStalker card) {
        super(card);
    }

    @Override
    public FenStalker copy() {
        return new FenStalker(this);
    }
}
