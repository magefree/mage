package mage.cards.k;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AnyPlayerControlsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofFromBlackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

public final class KnightOfGrace extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("black permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public KnightOfGrace(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FirstStrikeAbility.getInstance());
        addAbility(HexproofFromBlackAbility.getInstance());


        //Knight of Grace gets +1/+0 as long as any player controls a black permanent.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                new AnyPlayerControlsCondition(filter),
                "{this} gets +1/+0 as long as any player controls a black permanent.")));

    }

    private KnightOfGrace(final KnightOfGrace knightOfGrace){
        super(knightOfGrace);
    }

    public KnightOfGrace copy(){
        return new KnightOfGrace(this);
    }


}
