package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MichelangelosTechnique extends CardImpl {

    public MichelangelosTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Sneak {3}{G}
        this.addAbility(new SneakAbility(this, "{3}{G}"));

        // Look at the top eight cards of your library. Put up to two creature cards with total mana value 6 or less from among them onto the battlefield and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new MichelangelosTechniqueEffect());
    }

    private MichelangelosTechnique(final MichelangelosTechnique card) {
        super(card);
    }

    @Override
    public MichelangelosTechnique copy() {
        return new MichelangelosTechnique(this);
    }
}

class MichelangelosTechniqueEffect extends OneShotEffect {

    MichelangelosTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top eight cards of your library. Put up to two creature cards " +
                "with total mana value 6 or less from among them onto the battlefield " +
                "and the rest on the bottom of your library in a random order";
    }

    private MichelangelosTechniqueEffect(final MichelangelosTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public MichelangelosTechniqueEffect copy() {
        return new MichelangelosTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 8));
        TargetCard target = new MichelangelosTechniqueTarget();
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class MichelangelosTechniqueTarget extends TargetCardInLibrary {

    private static final FilterCard filterStatic = new FilterCreatureCard("creature cards with total mana value 6 or less");

    MichelangelosTechniqueTarget() {
        super(0, 2, filterStatic);
    }

    private MichelangelosTechniqueTarget(final MichelangelosTechniqueTarget target) {
        super(target);
    }

    @Override
    public MichelangelosTechniqueTarget copy() {
        return new MichelangelosTechniqueTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 6, game
        );
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(
                this.getTargets(), super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 6, game
        );
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
