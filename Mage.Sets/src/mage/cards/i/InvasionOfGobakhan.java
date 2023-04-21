package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfGobakhan extends CardImpl {

    public InvasionOfGobakhan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{1}{W}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(3);
        this.secondSideCardClazz = mage.cards.l.LightshieldArray.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Gobakhan enters the battlefield, look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InvasionOfGobakhanEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    private InvasionOfGobakhan(final InvasionOfGobakhan card) {
        super(card);
    }

    @Override
    public InvasionOfGobakhan copy() {
        return new InvasionOfGobakhan(this);
    }
}

class InvasionOfGobakhanEffect extends OneShotEffect {

    InvasionOfGobakhanEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand. You may exile a nonland card from it. " +
                "For as long as that card remains exiled, its owner may play it. " +
                "A spell cast this way costs {2} more to cast";
    }

    private InvasionOfGobakhanEffect(final InvasionOfGobakhanEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfGobakhanEffect copy() {
        return new InvasionOfGobakhanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand(
                0, 1, StaticFilters.FILTER_CARD_A_NON_LAND
        );
        controller.choose(outcome, opponent.getHand(), target, source, game);
        Card card = opponent.getHand().get(target.getFirstTarget(), game);
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        game.addEffect(new InvasionOfGobakhanCastEffect(card, game), source);
        game.addEffect(new InvasionOfGobakhanCostEffect(card, game), source);
        return true;
    }
}

class InvasionOfGobakhanCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    public InvasionOfGobakhanCastEffect(Card card, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
    }

    private InvasionOfGobakhanCastEffect(final InvasionOfGobakhanCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public InvasionOfGobakhanCastEffect copy() {
        return new InvasionOfGobakhanCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = mor.getCard(game);
        if (card == null) {
            discard();
            return false;
        }
        return mor.refersTo(CardUtil.getMainCardId(game, sourceId), game)
                && card.isOwnedBy(affectedControllerId);
    }
}

class InvasionOfGobakhanCostEffect extends CostModificationEffectImpl {

    private final MageObjectReference mor;

    InvasionOfGobakhanCostEffect(Card card, Game game) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.INCREASE_COST);
        mor = new MageObjectReference(card, game, 1);
    }

    private InvasionOfGobakhanCostEffect(InvasionOfGobakhanCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (game.inCheckPlayableState()) { // during playable check, the card is still in exile zone, the zcc is one less
            UUID cardtoCheckId = CardUtil.getMainCardId(game, abilityToModify.getSourceId());
            return mor.getSourceId().equals(cardtoCheckId)
                    && mor.getZoneChangeCounter() == game.getState().getZoneChangeCounter(cardtoCheckId) + 1;
        } else {
            return mor.refersTo(CardUtil.getMainCardId(game, abilityToModify.getSourceId()), game);
        }
    }

    @Override
    public InvasionOfGobakhanCostEffect copy() {
        return new InvasionOfGobakhanCostEffect(this);
    }
}
