package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.InklingToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmbralJuke extends CardImpl {

    public UmbralJuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose one: —
        // • Target player sacrifices a creature or planeswalker.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER, 1, "Target player"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // • Create a 2/1 white and black Inkling creature token with flying.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new InklingToken())));
    }

    private UmbralJuke(final UmbralJuke card) {
        super(card);
    }

    @Override
    public UmbralJuke copy() {
        return new UmbralJuke(this);
    }
}
