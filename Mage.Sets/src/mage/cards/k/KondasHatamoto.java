
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX
 */
public final class KondasHatamoto extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Legendary Samurai");
    private static final String rule1 = "As long as you control a legendary Samurai, {this} gets +1/+2";
    private static final String rule2 = "As long as you control a legendary Samurai, {this} has vigilance";

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(SubType.SAMURAI.getPredicate());
    }

    public KondasHatamoto (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(new BushidoAbility(1));

        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filter), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect1));
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));

    }

    private KondasHatamoto(final KondasHatamoto card) {
        super(card);
    }

    @Override
    public KondasHatamoto copy() {
        return new KondasHatamoto(this);
    }    
}
