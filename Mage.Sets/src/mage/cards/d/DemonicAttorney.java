package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class DemonicAttorney extends CardImpl {

    public DemonicAttorney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        

        // Remove Demonic Attorney from your deck before playing if you're not playing for ante.
        // Each player antes the top card of his or her library.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Demonic Attorney from your deck before playing " +
                "if you're not playing for ante.\nEach player antes the top card of his or her library."));
    }

    public DemonicAttorney(final DemonicAttorney card) {
        super(card);
    }

    @Override
    public DemonicAttorney copy() {
        return new DemonicAttorney(this);
    }
}
