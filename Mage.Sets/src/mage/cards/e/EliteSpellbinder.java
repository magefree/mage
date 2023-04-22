package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
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

/**
 * @author TheElk801
 */
public final class EliteSpellbinder extends CardImpl {

    public EliteSpellbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Elite Spellbinder enters the battlefield, look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EliteSpellbinderEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private EliteSpellbinder(final EliteSpellbinder card) {
        super(card);
    }

    @Override
    public EliteSpellbinder copy() {
        return new EliteSpellbinder(this);
    }
}

class EliteSpellbinderEffect extends OneShotEffect {

    EliteSpellbinderEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand. You may exile a nonland card from it. " +
                "For as long as that card remains exiled, its owner may play it. " +
                "A spell cast this way costs {2} more to cast";
    }

    private EliteSpellbinderEffect(final EliteSpellbinderEffect effect) {
        super(effect);
    }

    @Override
    public EliteSpellbinderEffect copy() {
        return new EliteSpellbinderEffect(this);
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
        game.addEffect(new EliteSpellbinderCastEffect(card, game), source);
        game.addEffect(new EliteSpellbinderCostEffect(card, game), source);
        return true;
    }
}

class EliteSpellbinderCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    public EliteSpellbinderCastEffect(Card card, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
    }

    private EliteSpellbinderCastEffect(final EliteSpellbinderCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EliteSpellbinderCastEffect copy() {
        return new EliteSpellbinderCastEffect(this);
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

class EliteSpellbinderCostEffect extends CostModificationEffectImpl {

    private final MageObjectReference mor;

    EliteSpellbinderCostEffect(Card card, Game game) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.INCREASE_COST);
        mor = new MageObjectReference(card, game, 1);
    }

    private EliteSpellbinderCostEffect(EliteSpellbinderCostEffect effect) {
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
    public EliteSpellbinderCostEffect copy() {
        return new EliteSpellbinderCostEffect(this);
    }
}
