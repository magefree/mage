package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LurkingRoper extends CardImpl {

    public LurkingRoper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Lurking Roper doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // Whenever you gain life, untap Lurking Roper.
        this.addAbility(new GainLifeControllerTriggeredAbility(new UntapSourceEffect()));
    }

    private LurkingRoper(final LurkingRoper card) {
        super(card);
    }

    @Override
    public LurkingRoper copy() {
        return new LurkingRoper(this);
    }
}
