package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AetherGust extends CardImpl {

    private static final FilterSpellOrPermanent filter
            = new FilterSpellOrPermanent("spell or permanent that's red or green");
    private static final Predicate predicate = Predicates.or(
            new ColorPredicate(ObjectColor.RED),
            new ColorPredicate(ObjectColor.GREEN)
    );

    static {
        filter.getPermanentFilter().add(predicate);
        filter.getSpellFilter().add(predicate);
    }

    public AetherGust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose target spell or permanent that's red or green. Its owner puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new AetherGustEffect());
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(1, 1, filter, false));
    }

    private AetherGust(final AetherGust card) {
        super(card);
    }

    @Override
    public AetherGust copy() {
        return new AetherGust(this);
    }
}

class AetherGustEffect extends OneShotEffect {

    AetherGustEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target spell or permanent that's red or green. " +
                "Its owner puts it on the top or bottom of their library.";
    }

    private AetherGustEffect(final AetherGustEffect effect) {
        super(effect);
    }

    @Override
    public AetherGustEffect copy() {
        return new AetherGustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Put the targeted object on the top or bottom of your library?",
                "", "Top", "Bottom", source, game)) {
            return new PutOnLibraryTargetEffect(true).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(false).apply(game, source);
    }
}