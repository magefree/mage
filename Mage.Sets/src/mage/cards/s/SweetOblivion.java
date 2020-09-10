package mage.cards.s;

import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SweetOblivion extends CardImpl {

    public SweetOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target player puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Escape—{3}{U}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{U}", 4));
    }

    private SweetOblivion(final SweetOblivion card) {
        super(card);
    }

    @Override
    public SweetOblivion copy() {
        return new SweetOblivion(this);
    }
}
