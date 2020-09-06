package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrotagNightRunner extends CardImpl {

    public GrotagNightRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Grotag Night-Runner deals combat damage to a player, exile the top card of your library. You may play that card this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GrotagNightRunnerExileEffect(), false));
    }

    private GrotagNightRunner(final GrotagNightRunner card) {
        super(card);
    }

    @Override
    public GrotagNightRunner copy() {
        return new GrotagNightRunner(this);
    }
}

class GrotagNightRunnerExileEffect extends OneShotEffect {

    GrotagNightRunnerExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. You may play that card this turn.";
    }

    private GrotagNightRunnerExileEffect(final GrotagNightRunnerExileEffect effect) {
        super(effect);
    }

    @Override
    public GrotagNightRunnerExileEffect copy() {
        return new GrotagNightRunnerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null || controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Library library = controller.getLibrary();
        Card card = library.getFromTop(game);
        if (card == null) {
            return true;
        }
        String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
        controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect, source);
        return true;
    }
}

class GrotagNightRunnerCastFromExileEffect extends AsThoughEffectImpl {

    GrotagNightRunnerCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    private GrotagNightRunnerCastFromExileEffect(final GrotagNightRunnerCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GrotagNightRunnerCastFromExileEffect copy() {
        return new GrotagNightRunnerCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
