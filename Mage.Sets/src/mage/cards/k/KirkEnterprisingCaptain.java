package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class KirkEnterprisingCaptain extends CardImpl {

    public KirkEnterprisingCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Kirk attacks while creatures you control have total power 8 or greater, exile the top card of your library. You may play that card this turn.
        this.addAbility(new AttacksTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
        ).withTriggerCondition(FormidableCondition.instance));
    }

    private KirkEnterprisingCaptain(final KirkEnterprisingCaptain card) {
        super(card);
    }

    @Override
    public KirkEnterprisingCaptain copy() {
        return new KirkEnterprisingCaptain(this);
    }
}
