package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MartyrsCry extends CardImpl {

    public MartyrsCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}");

        // Exile all white creatures. For each creature exiled this way, its controller draws a card.
        this.getSpellAbility().addEffect(new MartyrsCryEffect());
    }

    private MartyrsCry(final MartyrsCry card) {
        super(card);
    }

    @Override
    public MartyrsCry copy() {
        return new MartyrsCry(this);
    }
}

class MartyrsCryEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    MartyrsCryEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all white creatures. For each creature exiled this way, its controller draws a card.";
    }

    private MartyrsCryEffect(final MartyrsCryEffect effect) {
        super(effect);
    }

    @Override
    public MartyrsCryEffect copy() {
        return new MartyrsCryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        );
        Map<UUID, Integer> playerMap = permanents
                .stream()
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(
                        Function.identity(),
                        uuid -> 1,
                        Integer::sum
                ));
        controller.moveCards(new CardsImpl(permanents), Zone.EXILED, source, game);
        game.getState().processAction(game);
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            player.drawCards(entry.getValue(), source, game);
        }
        return true;
    }
}
