package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author fireshoes
 */
public final class AbbotOfKeralKeep extends CardImpl {

    public AbbotOfKeralKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Abbot of Keral Keep enters the battlefield, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1)));
    }

    private AbbotOfKeralKeep(final AbbotOfKeralKeep card) {
        super(card);
    }

    @Override
    public AbbotOfKeralKeep copy() {
        return new AbbotOfKeralKeep(this);
    }
}
