package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoraciousTyphon extends CardImpl {

    public VoraciousTyphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Escape—{5}{G}{G}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{5}{G}{G}", 4));

        // Voracious Typhon escapes with three +1/+1 counters on it.
        this.addAbility(new EscapesWithAbility(3));
    }

    private VoraciousTyphon(final VoraciousTyphon card) {
        super(card);
    }

    @Override
    public VoraciousTyphon copy() {
        return new VoraciousTyphon(this);
    }
}
