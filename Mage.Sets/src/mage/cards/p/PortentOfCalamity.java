package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.assignment.common.CardTypeAssignment;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class PortentOfCalamity extends CardImpl {

    public PortentOfCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");


        // Reveal the top X cards of your library. For each card type, you may exile a card of that type from among them. Put the rest into your graveyard. You may cast a spell from among the exiled cards without paying its mana cost if you exiled four or more cards this way. Then put the rest of the exiled cards into your hand.
        this.getSpellAbility().addEffect(new PortentOfCalamityEffect());
    }

    private PortentOfCalamity(final PortentOfCalamity card) {
        super(card);
    }

    @Override
    public PortentOfCalamity copy() {
        return new PortentOfCalamity(this);
    }
}

//Based on Atraxa, Grand Unifier
class PortentOfCalamityEffect extends OneShotEffect {

    PortentOfCalamityEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top X cards of your library. For each card type, you may exile a card of that type from among them. " +
                "Put the rest into your graveyard. You may cast a spell from among the exiled cards without paying its mana cost " +
                "if you exiled four or more cards this way. Then put the rest of the exiled cards into your hand";
    }

    private PortentOfCalamityEffect(final PortentOfCalamityEffect effect) {
        super(effect);
    }

    @Override
    public PortentOfCalamityEffect copy() {
        return new PortentOfCalamityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, CardUtil.getSourceCostsTag(game, source, "X", 0)));
        player.revealCards(source, cards, game);

        TargetCard target = new PortentOfCalamityTarget();
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Cards chosen = new CardsImpl(target.getTargets());
        player.moveCards(chosen, Zone.EXILED, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);

        if (chosen.size() >= 4){
            TargetCard freeCast = new TargetCard(0, 1, Zone.EXILED, StaticFilters.FILTER_CARD);
            player.choose(Outcome.PlayForFree, chosen, freeCast, source, game);
            Effect effect = new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST);
            effect.setTargetPointer(new FixedTarget(freeCast.getFirstTarget()));
            effect.apply(game, source);
        }
        chosen.retainZone(Zone.EXILED, game);
        player.moveCards(chosen, Zone.HAND, source, game);
        return true;
    }
}

class PortentOfCalamityTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a card of each card type");

    private static final CardTypeAssignment cardTypeAssigner
            = new CardTypeAssignment(Arrays.stream(CardType.values()).toArray(CardType[]::new));

    PortentOfCalamityTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private PortentOfCalamityTarget(final PortentOfCalamityTarget target) {
        super(target);
    }

    @Override
    public PortentOfCalamityTarget copy() {
        return new PortentOfCalamityTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}