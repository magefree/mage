package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mindleecher extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public Mindleecher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Mutate {4}{B}
        this.addAbility(new MutateAbility(this, "{4}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature mutates, exile the top card of each opponent's library face down. You may look at and play those cards for as long as they remain exiled.
        this.addAbility(new MutatesSourceTriggeredAbility(new MindleecherEffect()));
    }

    private Mindleecher(final Mindleecher card) {
        super(card);
    }

    @Override
    public Mindleecher copy() {
        return new Mindleecher(this);
    }
}

class MindleecherEffect extends OneShotEffect {

    MindleecherEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of each opponent's library face down. " +
                "You may look at and play those cards for as long as they remain exiled.";
    }

    private MindleecherEffect(final MindleecherEffect effect) {
        super(effect);
    }

    @Override
    public MindleecherEffect copy() {
        return new MindleecherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getOpponents(controller.getId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .forEach(cards::add);
        if (cards.isEmpty()) {
            return false;
        }
        return new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.NONE)
                .setTargetPointer(new FixedTargets(cards, game))
                .apply(game, source);
    }
}