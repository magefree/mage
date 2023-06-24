package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VesuvanDrifter extends CardImpl {

    public VesuvanDrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // At the beginning of each combat, you may reveal the top card of your library. If you reveal a creature card this way, Vesuvan Drifter becomes a copy of that card until end of turn, except it has flying.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new VesuvanDrifterEffect(), TargetController.ANY, true
        ));
    }

    private VesuvanDrifter(final VesuvanDrifter card) {
        super(card);
    }

    @Override
    public VesuvanDrifter copy() {
        return new VesuvanDrifter(this);
    }
}

class VesuvanDrifterEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.getAbilities().add(FlyingAbility.getInstance());
            return true;
        }
    };

    VesuvanDrifterEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. If you reveal a creature card this way, " +
                "{this} becomes a copy of that card until end of turn, except it has flying";
    }

    private VesuvanDrifterEffect(final VesuvanDrifterEffect effect) {
        super(effect);
    }

    @Override
    public VesuvanDrifterEffect copy() {
        return new VesuvanDrifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (!card.isCreature(game) || source.getSourcePermanentIfItStillExists(game) == null) {
            return true;
        }
        game.copyPermanent(
                Duration.EndOfTurn,
                new PermanentCard(card, source.getControllerId(), game),
                source.getSourceId(), source, applier
        );
        return true;
    }
}
