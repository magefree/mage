
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class IreShaman extends CardImpl {

    public IreShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());

        // Megamorph {R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{R}"), true));

        // When Ire Shaman is turned face up, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new IreShamanExileEffect(), false));
    }

    private IreShaman(final IreShaman card) {
        super(card);
    }

    @Override
    public IreShaman copy() {
        return new IreShaman(this);
    }
}

class IreShamanExileEffect extends OneShotEffect {

    public IreShamanExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. Until end of turn, you may play that card";
    }

    public IreShamanExileEffect(final IreShamanExileEffect effect) {
        super(effect);
    }

    @Override
    public IreShamanExileEffect copy() {
        return new IreShamanExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                ContinuousEffect effect = new IreShamanCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class IreShamanCastFromExileEffect extends AsThoughEffectImpl {

    public IreShamanCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public IreShamanCastFromExileEffect(final IreShamanCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IreShamanCastFromExileEffect copy() {
        return new IreShamanCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
