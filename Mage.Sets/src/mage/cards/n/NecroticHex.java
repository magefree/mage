package mage.cards.n;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NecroticHex extends CardImpl {

    public NecroticHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Each player sacrifices six creatures.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(
                6, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ).setText("each player sacrifices six creatures"));

        // You create six tapped 2/2 black creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new ZombieToken(), 6, true, false
        ).concatBy("You"));
    }

    private NecroticHex(final NecroticHex card) {
        super(card);
    }

    @Override
    public NecroticHex copy() {
        return new NecroticHex(this);
    }
}
