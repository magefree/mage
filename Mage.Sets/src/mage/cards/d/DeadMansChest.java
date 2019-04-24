package mage.cards.d;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DeadMansChest extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DeadMansChest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, exile cards equal to its power from the top of its owner's library. You may cast nonland cards from among them as long as they remain exiled, and you may spend mana as though it were mana of any type to cast those spells.
        this.addAbility(new DiesAttachedTriggeredAbility(new DeadMansChestEffect(), "enchanted creature", false));
    }

    public DeadMansChest(final DeadMansChest card) {
        super(card);
    }

    @Override
    public DeadMansChest copy() {
        return new DeadMansChest(this);
    }
}

class DeadMansChestEffect extends OneShotEffect {

    public DeadMansChestEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile cards equal to its power from the top of its owner's library. "
                + "You may cast nonland cards from among them as long as they remain exiled, "
                + "and you may spend mana as though it were mana of any type to cast those spells";
    }

    public DeadMansChestEffect(final DeadMansChestEffect effect) {
        super(effect);
    }

    @Override
    public DeadMansChestEffect copy() {
        return new DeadMansChestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Permanent attachedTo = (Permanent) getValue("attachedTo");
        if (controller != null && sourceObject != null && attachedTo != null) {
            Player owner = game.getPlayer(attachedTo.getOwnerId());
            int amount = attachedTo.getPower().getValue();
            if (owner != null && amount > 0) {
                Set<Card> cards = owner.getLibrary().getTopCards(game, amount);
                if (!cards.isEmpty()) {
                    controller.moveCardsToExile(cards, source, game, true, source.getSourceId(), sourceObject.getLogName());
                    for (Card card : cards) {
                        if (!card.isLand()) {
                            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.Custom);
                            effect.setTargetPointer(new FixedTarget(card, game));
                            game.addEffect(effect, source);
                            effect = new DeadMansChestSpendManaEffect();
                            effect.setTargetPointer(new FixedTarget(card, game));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DeadMansChestCastFromExileEffect extends AsThoughEffectImpl {

    public DeadMansChestCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast nonland cards from among them as long as they remain exiled";
    }

    public DeadMansChestCastFromExileEffect(final DeadMansChestCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DeadMansChestCastFromExileEffect copy() {
        return new DeadMansChestCastFromExileEffect(this);
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

class DeadMansChestSpendManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public DeadMansChestSpendManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "and you may spend mana as though it were mana of any type to cast those spells";
    }

    public DeadMansChestSpendManaEffect(final DeadMansChestSpendManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DeadMansChestSpendManaEffect copy() {
        return new DeadMansChestSpendManaEffect(this);
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
