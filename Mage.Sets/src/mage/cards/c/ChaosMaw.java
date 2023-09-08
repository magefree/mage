package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

public final class ChaosMaw extends CardImpl {
    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature");
    static {
        filter.add(AnotherPredicate.instance);
    }
    public ChaosMaw(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        subtype.add(SubType.HELLION);
        power = new MageInt(6);
        toughness = new MageInt(6);

        // When Chaos Maw enters the battlefield, it deals 3 damage to each other creature
        addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(3, "it", filter)));
    }

    private ChaosMaw(final ChaosMaw chaosMaw){
        super(chaosMaw);
    }

    public ChaosMaw copy(){
        return new ChaosMaw(this);
    }
}
