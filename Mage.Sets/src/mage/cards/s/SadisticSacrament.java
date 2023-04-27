package mage.cards.s;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class SadisticSacrament extends CardImpl {

    private static final String ruleText = "Search target player's library for up to three cards, exile them, " +
            "then that player shuffles. If this spell was kicked, instead search that player's library " +
            "for up to fifteen cards, exile them, then that player shuffles";

    public SadisticSacrament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");

        this.color.setBlack(true);

        // Kicker {7}
        this.addAbility(new KickerAbility("{7}"));

        // Search target player's library for up to three cards, exile them, then that player shuffles their library.
        // If Sadistic Sacrament was kicked, instead search that player's library for up to fifteen cards, exile them, then that player shuffles their library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryAndExileTargetEffect(15, true),
                new SearchLibraryAndExileTargetEffect(3, true),
                KickedCondition.ONCE, ruleText
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SadisticSacrament(final SadisticSacrament card) {
        super(card);
    }

    @Override
    public SadisticSacrament copy() {
        return new SadisticSacrament(this);
    }
}
