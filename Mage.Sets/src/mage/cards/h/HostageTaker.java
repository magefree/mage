
package mage.cards.h;

import java.util.UUID;
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
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class HostageTaker extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("another target artifact or creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    public HostageTaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Hostage Taker enters the battlefield, exile another target artifact or creature until Hostage Taker leaves the battlefield. You may cast that card as long as it remains exiled, and you may spend mana as though it were mana of any type to cast that spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HostageTakerExileEffect(filter.getMessage()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    public HostageTaker(final HostageTaker card) {
        super(card);
    }

    @Override
    public HostageTaker copy() {
        return new HostageTaker(this);
    }
}

class HostageTakerExileEffect extends OneShotEffect {

    public HostageTakerExileEffect(String targetName) {
        super(Outcome.Benefit);
        this.staticText = "exile another target artifact or creature until {this} leaves the battlefield. "
                + "You may cast that card as long as it remains exiled, "
                + "and you may spend mana as though it were mana of any type to cast that spell";
    }

    public HostageTakerExileEffect(final HostageTakerExileEffect effect) {
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
        if (permanent != null && card != null) {
            Player controller = game.getPlayer(card.getControllerId());
            if (controller != null) {
                // move card to exile
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                controller.moveCardToExileWithInfo(card, exileId, permanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
                // allow to cast the card
                ContinuousEffect effect = new HostageTakerCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
                // and you may spend mana as though it were mana of any color to cast it
                effect = new HostageTakerSpendAnyManaEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
}

class HostageTakerCastFromExileEffect extends AsThoughEffectImpl {

    public HostageTakerCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    public HostageTakerCastFromExileEffect(final HostageTakerCastFromExileEffect effect) {
        super(effect);
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                return true;
            }
        } else {
            if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
                // object has moved zone so effect can be discarted
                this.discard();
            }
        }
        return false;
    }
}

class HostageTakerSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public HostageTakerSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public HostageTakerSpendAnyManaEffect(final HostageTakerSpendAnyManaEffect effect) {
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
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {

            if (affectedControllerId.equals(source.getControllerId())) {
                // if the card moved from exile to spell the zone change counter is increased by 1
                if (game.getState().getZoneChangeCounter(objectId) == ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
                    return true;
                }
            }

        } else {
            if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
                // object has moved zone so effect can be discarted
                this.discard();
            }
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
