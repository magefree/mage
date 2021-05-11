
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
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
public final class VintaraSnapper extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

   public VintaraSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vintara Snapper has shroud as long as you control no untapped lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new GainAbilitySourceEffect(ShroudAbility.getInstance(),
            Duration.WhileOnBattlefield), new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
            "{this} has shroud as long as you control no untapped lands")));
    }

    private VintaraSnapper(final VintaraSnapper card) {
        super(card);
    }

    @Override
    public VintaraSnapper copy() {
        return new VintaraSnapper(this);
    }
}
