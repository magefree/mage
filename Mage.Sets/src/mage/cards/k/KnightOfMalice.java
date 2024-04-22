package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AnyPlayerControlsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofFromWhiteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

public final class KnightOfMalice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("white permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public KnightOfMalice(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FirstStrikeAbility.getInstance());
        addAbility(HexproofFromWhiteAbility.getInstance());

        //Knight of Malice gets +1/+0 as long as any player controls a white permanent.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                new AnyPlayerControlsCondition(filter),
                "{this} gets +1/+0 as long as any player controls a white permanent.")));

    }

    private KnightOfMalice(final KnightOfMalice knightOfGrace) {
        super(knightOfGrace);
    }

    public KnightOfMalice copy() {
        return new KnightOfMalice(this);
    }

}
