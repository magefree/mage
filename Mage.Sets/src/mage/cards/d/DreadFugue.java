package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author weirddan455
 */
public final class DreadFugue extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("nonland card [with mana value 2 or less]");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DreadFugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Cleave {2}{B}
        Ability ability = new CleaveAbility(this, new DreadFugueEffect(StaticFilters.FILTER_CARD_NON_LAND), "{2}{B}");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Target player reveals their hand. You may choose a nonland card from it [with mana value 2 or less]. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DreadFugueEffect(filter));
    }

    private DreadFugue(final DreadFugue card) {
        super(card);
    }

    @Override
    public DreadFugue copy() {
        return new DreadFugue(this);
    }
}

class DreadFugueEffect extends OneShotEffect {

    private final FilterCard filter;

    public DreadFugueEffect(FilterCard filter) {
        super(Outcome.Discard);
        this.filter = filter;
        staticText = "Target player reveals their hand. You choose a nonland card from it [with mana value 2 or less]. That player discards that card";
    }

    private DreadFugueEffect(final DreadFugueEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public DreadFugueEffect copy() {
        return new DreadFugueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        targetPlayer.revealCards(sourceCard != null ? sourceCard.getIdName() + " ("
                + sourceCard.getZoneChangeCounter(game) + ')' : "Discard", targetPlayer.getHand(), game);
        TargetCard target = new TargetCard(0, 1, Zone.HAND, filter);
        controller.choose(Outcome.Benefit, targetPlayer.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            targetPlayer.discard(card, false, source, game);
        }
        return true;
    }
}
