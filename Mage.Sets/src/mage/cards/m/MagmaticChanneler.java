package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaticChanneler extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final DynamicValue cardsCount
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
    private static final Hint hint
            = new ValueHint("Instant and sorcery cards in your graveyard", cardsCount);

    public MagmaticChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As long as there are four or more instant and/or sorcery cards in your graveyard, Magmatic Channeler gets +3/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 1, Duration.WhileOnBattlefield), condition,
                "as long as there are four or more instant and/or sorcery cards in your graveyard, {this} gets +3/+1"
        )).addHint(hint));

        // {T}, Discard a card: Exile the top two cards of your library, then choose one of them. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(new MagmaticChannelerExileEffect(), new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private MagmaticChanneler(final MagmaticChanneler card) {
        super(card);
    }

    @Override
    public MagmaticChanneler copy() {
        return new MagmaticChanneler(this);
    }
}

class MagmaticChannelerExileEffect extends OneShotEffect {

    MagmaticChannelerExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top two cards of your library, then choose one of them. You may play that card this turn.";
    }

    private MagmaticChannelerExileEffect(final MagmaticChannelerExileEffect effect) {
        super(effect);
    }

    @Override
    public MagmaticChannelerExileEffect copy() {
        return new MagmaticChannelerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        Card card = null;
        if (cards.isEmpty()) {
            return false;
        } else if (cards.size() == 1) {
            card = cards.getRandom(game);
        } else {
            TargetCard targetCard = new TargetCardInExile(StaticFilters.FILTER_CARD, null);
            player.choose(outcome, cards, targetCard, source, game);
            card = game.getCard(targetCard.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        game.addEffect(new MagmaticChannelerCastFromExileEffect(new MageObjectReference(card, game)), source);
        return true;
    }
}

class MagmaticChannelerCastFromExileEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    MagmaticChannelerCastFromExileEffect(MageObjectReference mor) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.mor = mor;
    }

    private MagmaticChannelerCastFromExileEffect(final MagmaticChannelerCastFromExileEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MagmaticChannelerCastFromExileEffect copy() {
        return new MagmaticChannelerCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && mor.refersTo(objectId, game);
    }
}
