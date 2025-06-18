package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouControlTwoOrMoreGatesCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SaruliGatekeepers extends CardImpl {

    public SaruliGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Saruli Gatekeepers enters the battlefield, if you control two or more Gates, gain 7 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(7))
                .withInterveningIf(YouControlTwoOrMoreGatesCondition.instance).addHint(GatesYouControlHint.instance));
    }

    private SaruliGatekeepers(final SaruliGatekeepers card) {
        super(card);
    }

    @Override
    public SaruliGatekeepers copy() {
        return new SaruliGatekeepers(this);
    }

}
