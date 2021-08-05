package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosChanneler extends CardImpl {

    public ChaosChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Wild Magic Surge — Whenever Chaos Channeler attacks, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new AttacksTriggeredAbility(effect).withFlavorWord("Wild Magic Surge"));

        // 1-9 | Exile the top card of your library. You may play it this turn.
        effect.addTableEntry(1, 9, new ExileTopXMayPlayUntilEndOfTurnEffect(1)
                .setText("exile the top card of your library. You may play it this turn"));

        // 10-19 | Exile the top two cards of your library. You may play them this turn.
        effect.addTableEntry(10, 19, new ExileTopXMayPlayUntilEndOfTurnEffect(2)
                .setText("exile the top two cards of your library. You may play them this turn"));

        // 20 | Exile the top three cards of your library. You may play them this turn.
        effect.addTableEntry(20, 20, new ExileTopXMayPlayUntilEndOfTurnEffect(3)
                .setText("exile the top three cards of your library. You may play them this turn"));
    }

    private ChaosChanneler(final ChaosChanneler card) {
        super(card);
    }

    @Override
    public ChaosChanneler copy() {
        return new ChaosChanneler(this);
    }
}
