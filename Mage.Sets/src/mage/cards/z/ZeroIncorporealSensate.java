package mage.cards.z;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class ZeroIncorporealSensate extends CardImpl {

    public ZeroIncorporealSensate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MEDUSAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Zero enters, look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ZeroIncorporealSensateEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ZeroIncorporealSensate(final ZeroIncorporealSensate card) {
        super(card);
    }

    @Override
    public ZeroIncorporealSensate copy() {
        return new ZeroIncorporealSensate(this);
    }
}

class ZeroIncorporealSensateEffect extends OneShotEffect {

    ZeroIncorporealSensateEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand. You may exile a nonland card from it. "
            + "For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast";
    }

    private ZeroIncorporealSensateEffect(final ZeroIncorporealSensateEffect effect) {
        super(effect);
    }

    @Override
    public ZeroIncorporealSensateEffect copy() {
        return new ZeroIncorporealSensateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        controller.lookAtCards(source, "Hand of " + opponent.getName(), opponent.getHand(), game);
        TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
        if (!controller.choose(outcome, opponent.getHand(), target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !controller.moveCards(card, Zone.EXILED, source, game)
            || game.getState().getZone(card.getId()) != Zone.EXILED) {
            return false;
        }
        game.addEffect(new ZeroIncorporealSensateCastEffect(card, game), source);
        game.addEffect(new ZeroIncorporealSensateCostEffect(card, game), source);
        return true;
    }
}

class ZeroIncorporealSensateCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    ZeroIncorporealSensateCastEffect(Card card, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
    }

    private ZeroIncorporealSensateCastEffect(final ZeroIncorporealSensateCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public ZeroIncorporealSensateCastEffect copy() {
        return new ZeroIncorporealSensateCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = mor.getCard(game);
        if (card == null) {
            discard();
            return false;
        }
        return mor.refersTo(CardUtil.getMainCardId(game, sourceId), game) && card.isOwnedBy(affectedControllerId);
    }
}

class ZeroIncorporealSensateCostEffect extends CostModificationEffectImpl {

    private final MageObjectReference mor;

    ZeroIncorporealSensateCostEffect(Card card, Game game) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.INCREASE_COST);
        mor = new MageObjectReference(card, game, 1);
    }

    private ZeroIncorporealSensateCostEffect(final ZeroIncorporealSensateCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public ZeroIncorporealSensateCostEffect copy() {
        return new ZeroIncorporealSensateCostEffect(this);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        if (game.inCheckPlayableState()) {
            UUID cardToCheckId = CardUtil.getMainCardId(game, abilityToModify.getSourceId());
            return mor.getSourceId().equals(cardToCheckId)
                    && mor.getZoneChangeCounter() == game.getState().getZoneChangeCounter(cardToCheckId) + 1;
        } else {
            return mor.refersTo(CardUtil.getMainCardId(game, abilityToModify.getSourceId()), game);
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }
}
