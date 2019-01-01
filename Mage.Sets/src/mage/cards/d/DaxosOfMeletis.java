package mage.cards.d;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class DaxosOfMeletis extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public DaxosOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DaxosOfMeletisEffect(), false, true));
    }

    public DaxosOfMeletis(final DaxosOfMeletis card) {
        super(card);
    }

    @Override
    public DaxosOfMeletis copy() {
        return new DaxosOfMeletis(this);
    }
}

class DaxosOfMeletisEffect extends OneShotEffect {

    public DaxosOfMeletisEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
    }

    public DaxosOfMeletisEffect(final DaxosOfMeletisEffect effect) {
        super(effect);
    }

    @Override
    public DaxosOfMeletisEffect copy() {
        return new DaxosOfMeletisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (damagedPlayer != null) {
                MageObject sourceObject = game.getObject(source.getSourceId());
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                Card card = damagedPlayer.getLibrary().getFromTop(game);
                if (card != null && sourceObject != null) {
                    // move card to exile
                    controller.moveCardsToExile(card, source, game, true, exileId, sourceObject.getIdName());
                    // player gains life
                    int cmc = card.getConvertedManaCost();
                    if (cmc > 0) {
                        controller.gainLife(cmc, game, source);
                    }
                    // Add effects only if the card has a spellAbility (e.g. not for lands).
                    if (card.getSpellAbility() != null) {
                        // allow to cast the card
                        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(card, game));
                        game.addEffect(effect, source);
                        // and you may spend mana as though it were mana of any color to cast it
                        effect = new DaxosOfMeletisSpendAnyManaEffect();
                        effect.setTargetPointer(new FixedTarget(card, game));
                        game.addEffect(effect, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class DaxosOfMeletisCastFromExileEffect extends AsThoughEffectImpl {

    private UUID cardId;
    private UUID exileId;

    public DaxosOfMeletisCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
        this.cardId = cardId;
        this.exileId = exileId;
    }

    public DaxosOfMeletisCastFromExileEffect(final DaxosOfMeletisCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DaxosOfMeletisCastFromExileEffect copy() {
        return new DaxosOfMeletisCastFromExileEffect(this);
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

class DaxosOfMeletisSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public DaxosOfMeletisSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public DaxosOfMeletisSpendAnyManaEffect(final DaxosOfMeletisSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DaxosOfMeletisSpendAnyManaEffect copy() {
        return new DaxosOfMeletisSpendAnyManaEffect(this);
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
