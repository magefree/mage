package mage.cards.t;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.*;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class TobiasBeckett extends CardImpl {

    public TobiasBeckett(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Tobias Becket enters the battlefield, put a bounty counter on target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Bounty - Whenever a creature an opponent controls with a bounty counter on it dies, exile the top card of that player's library. You may cast cards exiled this way and spend mana as though it were mana of any type to cast that spell.
        this.addAbility(new BountyAbility(new TobiasBeckettEffect(), false, true));
    }

    public TobiasBeckett(final TobiasBeckett card) {
        super(card);
    }

    @Override
    public TobiasBeckett copy() {
        return new TobiasBeckett(this);
    }
}

// Based on GrenzoHavocRaiserEffect
class TobiasBeckettEffect extends OneShotEffect {

    public TobiasBeckettEffect() {
        super(Outcome.Exile);
        staticText = "exile the top card of that player's library. You may cast cards exiled this way and spend mana as though it were mana of any type to cast that spell";
    }

    public TobiasBeckettEffect(final TobiasBeckettEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent bountyTriggered = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if(bountyTriggered != null) {
                Player opponent = game.getPlayer(bountyTriggered.getControllerId());
                if (opponent != null) {
                    MageObject sourceObject = game.getObject(source.getSourceId());
                    UUID exileId = CardUtil.getCardExileZoneId(game, source);
                    Card card = opponent.getLibrary().getFromTop(game);
                    if (card != null) {
                        // move card to exile
                        controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                        // Add effects only if the card has a spellAbility (e.g. not for lands).
                        if (card.getSpellAbility() != null) {
                            // allow to cast the card
                            game.addEffect(new TobiasBeckettCastFromExileEffect(card.getId(), exileId), source);
                            // and you may spend mana as though it were mana of any color to cast it
                            ContinuousEffect effect = new TobiasBeckettSpendAnyManaEffect();
                            effect.setTargetPointer(new FixedTarget(card.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TobiasBeckettEffect copy() {
        return new TobiasBeckettEffect(this);
    }
}

// Based on GrenzoHavocRaiserCastFromExileEffect
class TobiasBeckettCastFromExileEffect extends AsThoughEffectImpl {

    private UUID cardId;
    private UUID exileId;

    public TobiasBeckettCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast that card and you may spend mana as though it were mana of any color to cast it";
        this.cardId = cardId;
        this.exileId = exileId;
    }

    public TobiasBeckettCastFromExileEffect(final TobiasBeckettCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TobiasBeckettCastFromExileEffect copy() {
        return new TobiasBeckettCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(cardId) && source.isControlledBy(affectedControllerId)) {
            ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
            return exileZone != null && exileZone.contains(cardId);
        }
        return false;
    }
}

// Based on GrenzoHavocRaiserSpendAnyManaEffect
class TobiasBeckettSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public TobiasBeckettSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public TobiasBeckettSpendAnyManaEffect(final TobiasBeckettSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TobiasBeckettSpendAnyManaEffect copy() {
        return new TobiasBeckettSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, ((FixedTarget) getTargetPointer()).getTarget())
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && (((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId))
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

}
