
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.command.emblems.KioraMasterOfTheDepthsEmblem;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OctopusToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class KioraMasterOfTheDepths extends CardImpl {

    public KioraMasterOfTheDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIORA);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Untap up to one target creature and up to one target land.
        LoyaltyAbility ability1 = new LoyaltyAbility(new KioraUntapEffect(), 1);
        ability1.addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        ability1.addTarget(new TargetLandPermanent(0, 1, new FilterLandPermanent(), false));
        this.addAbility(ability1);

        // -2: Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard.
        this.addAbility(new LoyaltyAbility(new KioraRevealEffect(), -2));

        // -8: You get an emblem with "Whenever a creature enters the battlefield under your control, you may have it fight target creature." Then create three 8/8 blue Octopus creature tokens.
        Effect effect = new CreateTokenEffect(new OctopusToken(), 3);
        effect.setText("Then create three 8/8 blue Octopus creature tokens");
        LoyaltyAbility ability3 = new LoyaltyAbility(new GetEmblemEffect(new KioraMasterOfTheDepthsEmblem()), -8);
        ability3.addEffect(effect);
        this.addAbility(ability3);
    }

    public KioraMasterOfTheDepths(final KioraMasterOfTheDepths card) {
        super(card);
    }

    @Override
    public KioraMasterOfTheDepths copy() {
        return new KioraMasterOfTheDepths(this);
    }
}

class KioraUntapEffect extends OneShotEffect {

    public KioraUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "Untap up to one target creature and up to one target land";
    }

    public KioraUntapEffect(final KioraUntapEffect effect) {
        super(effect);
    }

    @Override
    public KioraUntapEffect copy() {
        return new KioraUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent firstTarget = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (firstTarget != null) {
            firstTarget.untap(game);
        }
        if (secondTarget != null) {
            return secondTarget.untap(game);
        }
        return true;
    }
}

class KioraRevealEffect extends OneShotEffect {

    public KioraRevealEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard";
    }

    public KioraRevealEffect(final KioraRevealEffect effect) {
        super(effect);
    }

    @Override
    public KioraRevealEffect copy() {
        return new KioraRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));
            boolean creatureCardFound = false;
            boolean landCardFound = false;
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    cards.add(card);
                    if (card.isCreature()) {
                        creatureCardFound = true;
                    }
                    if (card.isLand()) {
                        landCardFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                if ((creatureCardFound || landCardFound)
                        && controller.chooseUse(Outcome.DrawCard,
                                "Put a creature card and/or a land card into your hand?", source, game)) {
                    TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCreatureCard("creature card to put into your hand"));
                    if (creatureCardFound && controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }

                    target = new TargetCard(Zone.LIBRARY, new FilterLandCard("land card to put into your hand"));
                    if (landCardFound && controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                }
            }
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}
