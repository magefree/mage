package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.BreedingPitThrullToken;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;

/**
 * @author TheElk801
 */
public final class SzatsWill extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature they control with the greatest power");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }

    public SzatsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(ControlACommanderCondition.instance);

        // • Each opponent sacrifices a creature they control with the greatest power.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));

        // • Exile all cards from all opponents' graveyards, then create X 0/1 black Thrull creature tokens, where X is the greatest power among creature cards exiled this way.
        this.getSpellAbility().addMode(new Mode(new SzatsWillEffect()));
    }

    private SzatsWill(final SzatsWill card) {
        super(card);
    }

    @Override
    public SzatsWill copy() {
        return new SzatsWill(this);
    }
}

class SzatsWillEffect extends OneShotEffect {

    SzatsWillEffect() {
        super(Outcome.Benefit);
        staticText = "exile all opponents' graveyards, "
                + "then create X 0/1 black Thrull creature tokens, "
                + "where X is the greatest power among creature cards exiled this way";
    }

    private SzatsWillEffect(final SzatsWillEffect effect) {
        super(effect);
    }

    @Override
    public SzatsWillEffect copy() {
        return new SzatsWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        int maxPower = cards
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isCreature(game))
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        if (maxPower > 0) {
            new BreedingPitThrullToken().putOntoBattlefield(maxPower, game, source, source.getControllerId());
        }
        return true;
    }
}
