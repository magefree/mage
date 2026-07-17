package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlazingFiresinger extends PrepareCard {

    public BlazingFiresinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}", "Seething Song", CardType.INSTANT, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Seething Song
        // Instant {2}{R}
        // Add {R}{R}{R}{R}{R}.
        this.getSpellCard().getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(5)));
    }

    private BlazingFiresinger(final BlazingFiresinger card) {
        super(card);
    }

    @Override
    public BlazingFiresinger copy() {
        return new BlazingFiresinger(this);
    }
}
