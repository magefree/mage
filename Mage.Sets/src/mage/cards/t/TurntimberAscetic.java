package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurntimberAscetic extends CardImpl {

    public TurntimberAscetic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Turntimber Ascetic enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private TurntimberAscetic(final TurntimberAscetic card) {
        super(card);
    }

    @Override
    public TurntimberAscetic copy() {
        return new TurntimberAscetic(this);
    }
}
