
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class StonebrowKrosanHero extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with trample");
    static {
        filter.add(new AbilityPredicate(TrampleAbility.class));
    }

    public StonebrowKrosanHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Whenever a creature you control with trample attacks, it gets +2/+2 until end of turn.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("it gets +2/+2 until end of turn");
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(effect, false, filter, true));
    }

    private StonebrowKrosanHero(final StonebrowKrosanHero card) {
        super(card);
    }

    @Override
    public StonebrowKrosanHero copy() {
        return new StonebrowKrosanHero(this);
    }
}
