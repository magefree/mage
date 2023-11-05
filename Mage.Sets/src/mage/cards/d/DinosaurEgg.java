package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DinosaurEgg extends CardImpl {

    public DinosaurEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Evolve
        this.addAbility(new EvolveAbility());

        // When Dinosaur Egg dies, you may discover X, where X is its toughness.
        this.addAbility(new DiesSourceTriggeredAbility(new DinosaurEggEffect(), true));
    }

    private DinosaurEgg(final DinosaurEgg card) {
        super(card);
    }

    @Override
    public DinosaurEgg copy() {
        return new DinosaurEgg(this);
    }
}

class DinosaurEggEffect extends OneShotEffect {

    DinosaurEggEffect() {
        super(Outcome.Benefit);
        staticText = "discover X, where X is its toughness";
    }

    private DinosaurEggEffect(final DinosaurEggEffect effect) {
        super(effect);
    }

    @Override
    public DinosaurEggEffect copy() {
        return new DinosaurEggEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        DiscoverEffect.doDiscover(player, Math.max(0, permanent.getToughness().getValue()), game, source);
        return true;
    }
}
