package mage.cards.n;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class NarsetEnlightenedMaster extends CardImpl {

    public NarsetEnlightenedMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new NarsetEnlightenedMasterExileEffect(), false));

    }

    private NarsetEnlightenedMaster(final NarsetEnlightenedMaster card) {
        super(card);
    }

    @Override
    public NarsetEnlightenedMaster copy() {
        return new NarsetEnlightenedMaster(this);
    }
}

class NarsetEnlightenedMasterExileEffect extends OneShotEffect {

    public NarsetEnlightenedMasterExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with {this} this turn without paying their mana costs";
    }

    public NarsetEnlightenedMasterExileEffect(final NarsetEnlightenedMasterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            Set<Card> cards = player.getLibrary().getTopCards(game, 4);
            player.moveCards(cards, Zone.EXILED, source, game);
            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED
                        && !card.isCreature(game)
                        && !card.isLand(game)) {
                    ContinuousEffect effect = new NarsetEnlightenedMasterCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public NarsetEnlightenedMasterExileEffect copy() {
        return new NarsetEnlightenedMasterExileEffect(this);
    }
}

class NarsetEnlightenedMasterCastFromExileEffect extends AsThoughEffectImpl {

    public NarsetEnlightenedMasterCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may cast noncreature cards exiled with {this} this turn without paying their mana costs";
    }

    public NarsetEnlightenedMasterCastFromExileEffect(final NarsetEnlightenedMasterCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NarsetEnlightenedMasterCastFromExileEffect copy() {
        return new NarsetEnlightenedMasterCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Player player = game.getPlayer(affectedControllerId);
            if (player != null) {
                return allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
            }
        }
        return false;
    }
}
