package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CastAwayDoubt extends CardImpl {

    public CastAwayDoubt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Draw two cards. Cast Away Doubt deals 2 damage to each player.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new DamagePlayersEffect(2));
    }

    private CastAwayDoubt(final CastAwayDoubt card) {
        super(card);
    }

    @Override
    public CastAwayDoubt copy() {
        return new CastAwayDoubt(this);
    }
}
