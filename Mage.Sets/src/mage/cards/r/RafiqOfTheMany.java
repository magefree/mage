package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class RafiqOfTheMany extends CardImpl {

    public RafiqOfTheMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, it gains double strike until end of turn.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains double strike until end of turn"), true, false));
    }

    private RafiqOfTheMany(final RafiqOfTheMany card) {
        super(card);
    }

    @Override
    public RafiqOfTheMany copy() {
        return new RafiqOfTheMany(this);
    }

}
