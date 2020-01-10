package mage.cards.l;

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
public final class LoathsomeChimera extends CardImpl {

    public LoathsomeChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Escape-{4}{G}, exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{4}{G}", 3));

        // Loathsome Chimera escapes with a +1/+1 counter on it.
        this.addAbility(new EscapesWithAbility(1));
    }

    private LoathsomeChimera(final LoathsomeChimera card) {
        super(card);
    }

    @Override
    public LoathsomeChimera copy() {
        return new LoathsomeChimera(this);
    }
}
