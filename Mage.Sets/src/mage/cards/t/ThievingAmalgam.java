package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievingAmalgam extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public ThievingAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // At the beginning of each opponent's upkeep, you manifest the top card of that player's library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ThievingAmalgamManifestEffect(), TargetController.OPPONENT, false
        ));

        // Whenever a creature you control but don't own dies, its owner loses 2 life and you gain 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ThievingAmalgamLifeLossEffect(), false, filter, true
        ));
    }

    private ThievingAmalgam(final ThievingAmalgam card) {
        super(card);
    }

    @Override
    public ThievingAmalgam copy() {
        return new ThievingAmalgam(this);
    }
}

class ThievingAmalgamManifestEffect extends OneShotEffect {

    ThievingAmalgamManifestEffect() {
        super(Outcome.Benefit);
        staticText = "you manifest the top card of that player's library";
    }

    private ThievingAmalgamManifestEffect(final ThievingAmalgamManifestEffect effect) {
        super(effect);
    }

    @Override
    public ThievingAmalgamManifestEffect copy() {
        return new ThievingAmalgamManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player active = game.getPlayer(game.getActivePlayerId());
        if (controller == null || active == null) {
            return false;
        }
        Ability newSource = source.copy();
        newSource.setWorksFaceDown(true);
        Set<Card> cards = active.getLibrary().getTopCards(game, 1);
        cards.stream().forEach(card -> {
            ManaCosts manaCosts = null;
            if (card.isCreature(game)) {
                manaCosts = card.getSpellAbility() != null ? card.getSpellAbility().getManaCosts() : null;
                if (manaCosts == null) {
                    manaCosts = new ManaCostsImpl<>("{0}");
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

class ThievingAmalgamLifeLossEffect extends OneShotEffect {

    private static final Effect effect = new GainLifeEffect(2);

    ThievingAmalgamLifeLossEffect() {
        super(Outcome.Benefit);
        staticText = "its owner loses 2 life and you gain 2 life";
    }

    private ThievingAmalgamLifeLossEffect(final ThievingAmalgamLifeLossEffect effect) {
        super(effect);
    }

    @Override
    public ThievingAmalgamLifeLossEffect copy() {
        return new ThievingAmalgamLifeLossEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(targetPointer.getFirst(game, source)));
        if (player == null) {
            return false;
        }
        player.loseLife(2, game, source, false);
        return effect.apply(game, source);
    }
}
