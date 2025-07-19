package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmissaryEscort extends CardImpl {

    public EmissaryEscort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // This creature gets +X/+0 where X is the greatest mana value among other artifacts you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                GreatestAmongPermanentsValue.MANAVALUE_OTHER_CONTROLLED_ARTIFACTS,
                StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(GreatestAmongPermanentsValue.MANAVALUE_OTHER_CONTROLLED_ARTIFACTS.getHint()));
    }

    private EmissaryEscort(final EmissaryEscort card) {
        super(card);
    }

    @Override
    public EmissaryEscort copy() {
        return new EmissaryEscort(this);
    }
}
