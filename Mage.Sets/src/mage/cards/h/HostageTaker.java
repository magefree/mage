package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HostageTaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
    }

    public HostageTaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Hostage Taker enters the battlefield, exile another target artifact or creature until Hostage Taker leaves the battlefield. You may cast that card as long as it remains exiled, and you may spend mana as though it were mana of any type to cast that spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HostageTakerExileEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private HostageTaker(final HostageTaker card) {
        super(card);
    }

    @Override
    public HostageTaker copy() {
        return new HostageTaker(this);
    }
}

class HostageTakerExileEffect extends OneShotEffect {

    HostageTakerExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile another target artifact or creature until {this} leaves the battlefield. "
                + "You may cast that card as long as it remains exiled, "
                + "and you may spend mana as though it were mana of any type to cast that spell";
    }

    private HostageTakerExileEffect(final HostageTakerExileEffect effect) {
        super(effect);
    }

    @Override
    public HostageTakerExileEffect copy() {
        return new HostageTakerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent card = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || card == null) {
            return false;
        }
        Player controller = game.getPlayer(card.getControllerId());
        if (controller == null) {
            return false;
        }
        // move card to exile
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        controller.moveCardToExileWithInfo(card, exileId, permanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
        // allow to cast the card
        game.addEffect(new HostageTakerCastFromExileEffect(card.getId(), exileId), source);
        // and you may spend mana as though it were mana of any color to cast it
        ContinuousEffect effect = new HostageTakerSpendAnyManaEffect();
        effect.setTargetPointer(new FixedTarget(card.getId(), game));
        game.addEffect(effect, source);
        return false;
    }
}

class HostageTakerCastFromExileEffect extends AsThoughEffectImpl {

    private UUID cardId;
    private UUID exileId;

    HostageTakerCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.cardId = cardId;
        this.exileId = exileId;
    }

    private HostageTakerCastFromExileEffect(final HostageTakerCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HostageTakerCastFromExileEffect copy() {
        return new HostageTakerCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!sourceId.equals(cardId) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
        if (exileZone != null && exileZone.contains(cardId)) {
            return true;
        }
        discard();
        return false;
    }
}

class HostageTakerSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    HostageTakerSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
    }

    private HostageTakerSpendAnyManaEffect(final HostageTakerSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HostageTakerSpendAnyManaEffect copy() {
        return new HostageTakerSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, fixedTarget.getTarget())
                && fixedTarget.getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
