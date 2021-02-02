package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class AbbotOfKeralKeep extends CardImpl {

    public AbbotOfKeralKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Abbot of Keral Keep enters the battlefield, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AbbotOfKeralKeepExileEffect()));
    }

    private AbbotOfKeralKeep(final AbbotOfKeralKeep card) {
        super(card);
    }

    @Override
    public AbbotOfKeralKeep copy() {
        return new AbbotOfKeralKeep(this);
    }
}

class AbbotOfKeralKeepExileEffect extends OneShotEffect {

    public AbbotOfKeralKeepExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. Until end of turn, you may play that card";
    }

    public AbbotOfKeralKeepExileEffect(final AbbotOfKeralKeepExileEffect effect) {
        super(effect);
    }

    @Override
    public AbbotOfKeralKeepExileEffect copy() {
        return new AbbotOfKeralKeepExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().hasCards()) {
            Library library = controller.getLibrary();
            Card card = library.getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class AbbotOfKeralKeepCastFromExileEffect extends AsThoughEffectImpl {

    public AbbotOfKeralKeepCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public AbbotOfKeralKeepCastFromExileEffect(final AbbotOfKeralKeepCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AbbotOfKeralKeepCastFromExileEffect copy() {
        return new AbbotOfKeralKeepCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
