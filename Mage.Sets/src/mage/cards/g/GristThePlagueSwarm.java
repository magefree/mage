package mage.cards.g;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.token.IzoniInsectToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class GristThePlagueSwarm extends CardImpl {

    public GristThePlagueSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GRIST);
        this.setStartingLoyalty(3);

        this.color.setBlack(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // +1: Create a 1/1 black and green Insect creature token, then mill two cards. Put a deathtouch counter on the token if a black card was milled this way.
        this.addAbility(new LoyaltyAbility(new GristThePlagueSwarmPlus1Effect(), 1));

        // -2: Destroy target artifact or enchantment.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // -6: For each creature card in your graveyard, create a token that's a copy of it, except it's a 1/1 black and green Insect.
        this.addAbility(new LoyaltyAbility(new GristThePlagueSwarmMinus6Effect(), -6));
    }

    private GristThePlagueSwarm(final GristThePlagueSwarm card) {
        super(card);
    }

    @Override
    public GristThePlagueSwarm copy() {
        return new GristThePlagueSwarm(this);
    }
}

class GristThePlagueSwarmPlus1Effect extends OneShotEffect {

    GristThePlagueSwarmPlus1Effect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a 1/1 black and green Insect creature token, then mill two cards. "
                + "Put a deathtouch counter on the token if a black card was milled this way.";
    }

    private GristThePlagueSwarmPlus1Effect(final GristThePlagueSwarmPlus1Effect effect) {
        super(effect);
    }

    @Override
    public GristThePlagueSwarmPlus1Effect copy() {
        return new GristThePlagueSwarmPlus1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Create a 1/1 black and green Insect creature token
        CreateTokenEffect effect = new CreateTokenEffect(new IzoniInsectToken());
        effect.apply(game, source);

        // Then mill two cards
        Cards cards = controller.millCards(2, source, game);

        // Put a deathtouch counter on the token if a black card was milled this way.
        if (cards.getCards(game).stream().anyMatch(card -> card.getColor(game).isBlack())) {
            List<Permanent> tokens = effect
                    .getLastAddedTokenIds()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!tokens.isEmpty()) {
                Effect addEffect = new AddCountersTargetEffect(CounterType.DEATHTOUCH.createInstance());
                addEffect.setTargetPointer(new FixedTargets(tokens, game));
                addEffect.apply(game, source);
            }
        }
        return true;
    }
}

class GristThePlagueSwarmMinus6Effect extends OneShotEffect {

    GristThePlagueSwarmMinus6Effect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "For each creature card in your graveyard, create a token that's a copy of it, "
                + "except it's a 1/1 black and green Insect.";
    }

    private GristThePlagueSwarmMinus6Effect(final GristThePlagueSwarmMinus6Effect effect) {
        super(effect);
    }

    @Override
    public GristThePlagueSwarmMinus6Effect copy() {
        return new GristThePlagueSwarmMinus6Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game);
        if (cards.isEmpty()) {
            return false;
        }
        for (Card card : cards) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                    null, null, false, 1, false,
                    false, null, 1, 1, false
            );
            effect.setSavedPermanent(new PermanentCard(card, controller.getId(), game));
            effect.setOnlyColor(new ObjectColor("BG"));
            effect.setOnlySubType(SubType.INSECT);
            effect.apply(game, source);
        }
        return true;
    }

}