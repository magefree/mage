package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GolosTirelessPilgrim extends CardImpl {

    public GolosTirelessPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Golos, Tireless Pilgrim enters the battlefield, you may search your library for a land card, put that card onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A), true
        ), true));

        // {2}{W}{U}{B}{R}{G}: Exile the top three cards of your library. You may play them this turn without paying their mana costs.
        this.addAbility(new SimpleActivatedAbility(
                new GolosTirelessPilgrimEffect(),
                new ManaCostsImpl("{2}{W}{U}{B}{R}{G}")
        ));
    }

    private GolosTirelessPilgrim(final GolosTirelessPilgrim card) {
        super(card);
    }

    @Override
    public GolosTirelessPilgrim copy() {
        return new GolosTirelessPilgrim(this);
    }
}

class GolosTirelessPilgrimEffect extends OneShotEffect {

    GolosTirelessPilgrimEffect() {
        super(Outcome.Discard);
        staticText = "Exile the top three cards of your library. " +
                "You may play them this turn without paying their mana costs.";
    }

    private GolosTirelessPilgrimEffect(final GolosTirelessPilgrimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = player.getLibrary().getTopCards(game, 3);
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.stream()
                .filter(card -> game.getState().getZone(card.getId()) == Zone.EXILED)
                .forEach(card -> {
                    ContinuousEffect effect = new GolosTirelessPilgrimCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                });
        return true;
    }

    @Override
    public GolosTirelessPilgrimEffect copy() {
        return new GolosTirelessPilgrimEffect(this);
    }
}

class GolosTirelessPilgrimCastFromExileEffect extends AsThoughEffectImpl {

    GolosTirelessPilgrimCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private GolosTirelessPilgrimCastFromExileEffect(final GolosTirelessPilgrimCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GolosTirelessPilgrimCastFromExileEffect copy() {
        return new GolosTirelessPilgrimCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!objectId.equals(getTargetPointer().getFirst(game, source))
                || !affectedControllerId.equals(source.getControllerId())) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand() || card.getSpellAbility().getCosts() == null) {
            return true;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player != null) {
            player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
        }
        return true;
    }
}
