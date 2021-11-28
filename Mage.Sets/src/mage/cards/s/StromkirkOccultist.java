
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class StromkirkOccultist extends CardImpl {

    public StromkirkOccultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Stromkirk Mystic deals combat damage to a player, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new StromkirkOccultistExileEffect(), false));

        // Madness {1}{R}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{R}")));
    }

    private StromkirkOccultist(final StromkirkOccultist card) {
        super(card);
    }

    @Override
    public StromkirkOccultist copy() {
        return new StromkirkOccultist(this);
    }
}

class StromkirkOccultistExileEffect extends OneShotEffect {

    public StromkirkOccultistExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. Until end of turn, you may play that card";
    }

    public StromkirkOccultistExileEffect(final StromkirkOccultistExileEffect effect) {
        super(effect);
    }

    @Override
    public StromkirkOccultistExileEffect copy() {
        return new StromkirkOccultistExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
                if (controller.moveCardToExileWithInfo(card, source.getSourceId(), exileName, source, game, Zone.LIBRARY, true)) {
                    ContinuousEffect effect = new StromkirkOccultistPlayFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class StromkirkOccultistPlayFromExileEffect extends AsThoughEffectImpl {

    public StromkirkOccultistPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public StromkirkOccultistPlayFromExileEffect(final StromkirkOccultistPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StromkirkOccultistPlayFromExileEffect copy() {
        return new StromkirkOccultistPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
