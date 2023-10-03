package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author BursegSardaukar
 */
public final class ShriekingMogg extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public ShriekingMogg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Shrieking Mogg enters the battlefield, tap all other creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapAllEffect(filter));
        this.addAbility(ability);
    }

    private ShriekingMogg(final ShriekingMogg card) {
        super(card);
    }

    @Override
    public ShriekingMogg copy() {
        return new ShriekingMogg(this);
    }

}
