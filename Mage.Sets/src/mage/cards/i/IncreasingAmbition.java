package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IncreasingAmbition extends CardImpl {

    public IncreasingAmbition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Search your library for a card and put that card into your hand. If this spell was cast from a graveyard, instead search your library for two cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(2, StaticFilters.FILTER_CARD)),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary()),
                IncreasingAmbitionCondition.instance, "Search your library for a card " +
                "and put that card into your hand. If this spell was cast from a graveyard, " +
                "instead search your library for two cards and put those cards into your hand. " +
                "Then shuffle."
        ));

        // Flashback {7}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{7}{B}")));
    }

    private IncreasingAmbition(final IncreasingAmbition card) {
        super(card);
    }

    @Override
    public IncreasingAmbition copy() {
        return new IncreasingAmbition(this);
    }
}

enum IncreasingAmbitionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getSourceId());
        return spell != null && spell.getFromZone() == Zone.GRAVEYARD;
    }
}
