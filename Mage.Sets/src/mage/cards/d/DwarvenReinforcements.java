package mage.cards.d;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DwarfBerserkerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DwarvenReinforcements extends CardImpl {

    public DwarvenReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Create two 2/1 red Dwarf Berserker creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DwarfBerserkerToken(), 2));

        // Foretell {1}{R}
        this.addAbility(new ForetellAbility(this, "{1}{R}"));
    }

    private DwarvenReinforcements(final DwarvenReinforcements card) {
        super(card);
    }

    @Override
    public DwarvenReinforcements copy() {
        return new DwarvenReinforcements(this);
    }
}
