package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 * @author jeffwadsworth
 */
public final class Gomazoa extends CardImpl {

    public Gomazoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.JELLYFISH);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {tap}: Put Gomazoa and each creature it's blocking on top of their owners' libraries, then those players shuffle their libraries.
        this.addAbility(new SimpleActivatedAbility(new GomazoaEffect(), new TapSourceCost()));

    }

    private Gomazoa(final Gomazoa card) {
        super(card);
    }

    @Override
    public Gomazoa copy() {
        return new Gomazoa(this);
    }
}

class GomazoaEffect extends OneShotEffect {

    GomazoaEffect() {
        super(Outcome.Neutral);
        this.staticText = "Put {this} and each creature it's blocking on top of their owners' libraries, then those players shuffle";
    }

    private GomazoaEffect(final GomazoaEffect effect) {
        super(effect);
    }

    @Override
    public GomazoaEffect copy() {
        return new GomazoaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> permanents = new ArrayList<>();
        Set<UUID> playersToShuffle = new HashSet<>();
        Permanent gomazoa = source.getSourcePermanentIfItStillExists(game);
        if (gomazoa != null) {
            permanents.add(gomazoa);
            playersToShuffle.add(gomazoa.getOwnerId());
        } else {
            gomazoa = source.getSourcePermanentOrLKI(game);
        }
        if (gomazoa != null) {
            gomazoa.getBlockingRefs().stream()
                    .map(mor -> mor.getPermanent(game))
                    .filter(Objects::nonNull)
                    .forEach(blocked -> {
                        permanents.add(blocked);
                        playersToShuffle.add(blocked.getOwnerId());
                    });
        }
        for (Permanent permanent : permanents) {
            controller.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, true);
        }
        playersToShuffle.stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEach(p -> p.shuffleLibrary(source, game));
        return true;
    }
}
