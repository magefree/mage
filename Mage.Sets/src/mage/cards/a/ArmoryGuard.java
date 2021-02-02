
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ArmoryGuard extends CardImpl {
    
    private static final String rule = "Armory Guard has vigilance as long as you control a Gate";
    
    private static final FilterPermanent filter = new FilterPermanent("Gate");
    
    static {
        filter.add(SubType.GATE.getPredicate());
    }

    public ArmoryGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Armory Guard has vigilance as long as you control a Gate.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private ArmoryGuard(final ArmoryGuard card) {
        super(card);
    }

    @Override
    public ArmoryGuard copy() {
        return new ArmoryGuard(this);
    }
}
