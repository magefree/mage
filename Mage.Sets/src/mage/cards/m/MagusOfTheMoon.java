package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.NonbasicLandsAreMountainsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MagusOfTheMoon extends CardImpl {

    public MagusOfTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(new NonbasicLandsAreMountainsEffect()));
    }

    private MagusOfTheMoon(final MagusOfTheMoon card) {
        super(card);
    }

    @Override
    public MagusOfTheMoon copy() {
        return new MagusOfTheMoon(this);
    }
}
