package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdPledgemage extends CardImpl {

    public LoreholdPledgemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}{R/W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Lorehold Pledgemage gets +1/+0 until end of turn.
        this.addAbility(new MagecraftAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private LoreholdPledgemage(final LoreholdPledgemage card) {
        super(card);
    }

    @Override
    public LoreholdPledgemage copy() {
        return new LoreholdPledgemage(this);
    }
}
