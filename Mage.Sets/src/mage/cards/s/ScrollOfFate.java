package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ScrollOfFate extends CardImpl {

    public ScrollOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Manifest a card from your hand.
        this.addAbility(new SimpleActivatedAbility(new ScrollOfFateEffect(), new TapSourceCost()));
    }

    private ScrollOfFate(final ScrollOfFate card) {
        super(card);
    }

    @Override
    public ScrollOfFate copy() {
        return new ScrollOfFate(this);
    }
}

class ScrollOfFateEffect extends OneShotEffect {

    ScrollOfFateEffect() {
        super(Outcome.Benefit);
        staticText = "manifest a card from your hand";
    }

    private ScrollOfFateEffect(final ScrollOfFateEffect effect) {
        super(effect);
    }

    @Override
    public ScrollOfFateEffect copy() {
        return new ScrollOfFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || controller.getHand().isEmpty()) {
            return false;
        }
        TargetCard targetCard = new TargetCardInHand();
        if (!controller.choose(outcome, controller.getHand(), targetCard, game)) {
            return false;
        }
        Ability newSource = source.copy();
        newSource.setWorksFaceDown(true);
        Set<Card> cards = targetCard
                .getTargets()
                .stream()
                .map(game::getCard)
                .collect(Collectors.toSet());
        cards.stream().forEach(card -> {
            ManaCosts manaCosts = null;
            if (card.isCreature()) {
                manaCosts = card.getSpellAbility() != null ? card.getSpellAbility().getManaCosts() : null;
                if (manaCosts == null) {
                    manaCosts = new ManaCostsImpl("{0}");
                }
            }
            MageObjectReference objectReference = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
            game.addEffect(new BecomesFaceDownCreatureEffect(manaCosts, objectReference, Duration.Custom, BecomesFaceDownCreatureEffect.FaceDownType.MANIFESTED), newSource);
        });
        controller.moveCards(cards, Zone.BATTLEFIELD, source, game, false, true, false, null);
        cards.stream()
                .map(Card::getId)
                .map(game::getPermanent)
                .filter(permanent -> permanent != null)
                .forEach(permanent -> permanent.setManifested(true));
        return true;
    }
}