package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManaTypeInManaPoolCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.YouDontLoseManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OmnathLocusOfMana extends CardImpl {

    private static final DynamicValue xValue = new ManaTypeInManaPoolCount(ManaType.GREEN);

    public OmnathLocusOfMana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Green mana doesn't empty from your mana pool as steps and phases end.
        this.addAbility(new SimpleStaticAbility(new YouDontLoseManaEffect(ManaType.GREEN)));

        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));
    }

    private OmnathLocusOfMana(final OmnathLocusOfMana card) {
        super(card);
    }

    @Override
    public OmnathLocusOfMana copy() {
        return new OmnathLocusOfMana(this);
    }
}
