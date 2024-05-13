package mage.cards.b;

import mage.MageInt;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.HexproofFromEachColorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreakerOfCreation extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("colorless permanent you control");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);

    public BreakerOfCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // When you cast this spell, you gain 1 life for each colorless permanent you control.
        this.addAbility(new CastSourceTriggeredAbility(new GainLifeEffect(xValue)));

        // Hexproof from each color
        this.addAbility(HexproofFromEachColorAbility.getInstance());

        // Annihilator 2
        this.addAbility(new AnnihilatorAbility(2));
    }

    private BreakerOfCreation(final BreakerOfCreation card) {
        super(card);
    }

    @Override
    public BreakerOfCreation copy() {
        return new BreakerOfCreation(this);
    }
}
