package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Agent13SharonCarter extends CardImpl {

    public Agent13SharonCarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a creature you control attacks alone, investigate.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new InvestigateEffect()));
    }

    private Agent13SharonCarter(final Agent13SharonCarter card) {
        super(card);
    }

    @Override
    public Agent13SharonCarter copy() {
        return new Agent13SharonCarter(this);
    }
}
