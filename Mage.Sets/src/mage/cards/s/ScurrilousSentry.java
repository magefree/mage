package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScurrilousSentry extends CardImpl {

    public ScurrilousSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Scurrilous Sentry enters the battlefield or attacks, it connives.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ConniveSourceEffect()));
    }

    private ScurrilousSentry(final ScurrilousSentry card) {
        super(card);
    }

    @Override
    public ScurrilousSentry copy() {
        return new ScurrilousSentry(this);
    }
}
