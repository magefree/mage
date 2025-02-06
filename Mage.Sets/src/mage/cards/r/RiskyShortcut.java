package mage.cards.r;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiskyShortcut extends CardImpl {

    public RiskyShortcut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Draw two cards. Each player loses 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(2));
    }

    private RiskyShortcut(final RiskyShortcut card) {
        super(card);
    }

    @Override
    public RiskyShortcut copy() {
        return new RiskyShortcut(this);
    }
}
