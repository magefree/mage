package mage.cards.r;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class ReforgeTheSoul extends CardImpl {

    public ReforgeTheSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Each player discards their hand, then draws seven cards.
        this.getSpellAbility().addEffect(new DiscardHandAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);

        // Miracle {1}{R}
        this.addAbility(new MiracleAbility("{1}{R}"));
    }

    private ReforgeTheSoul(final ReforgeTheSoul card) {
        super(card);
    }

    @Override
    public ReforgeTheSoul copy() {
        return new ReforgeTheSoul(this);
    }
}
