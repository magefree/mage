package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class UnstableAmulet extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public UnstableAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // When Unstable Amulet enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever you cast a spell from anywhere other than your hand, Unstable Amulet deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                filter, false
        ));

        // {T}, Pay {E}{E}: Exile the top card of your library. You may play it until you exile another card with Unstable Amulet.
        Ability ability = new SimpleActivatedAbility(
                new UnstableAmuletEffect(),
                new TapSourceCost()
        );
        ability.addCost(new PayEnergyCost(2));
        this.addAbility(ability);
    }

    private UnstableAmulet(final UnstableAmulet card) {
        super(card);
    }

    @Override
    public UnstableAmulet copy() {
        return new UnstableAmulet(this);
    }
}

class UnstableAmuletEffect extends OneShotEffect {

    UnstableAmuletEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile the top card of your library. You may play it until you exile another card with {this}.";
    }

    private UnstableAmuletEffect(final UnstableAmuletEffect effect) {
        super(effect);
    }

    @Override
    public UnstableAmuletEffect copy() {
        return new UnstableAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        String exileName = CardUtil.getSourceIdName(game, source);
        controller.moveCardsToExile(card, source, game, true, exileId, exileName);
        game.getState().processAction(game);
        if (!Zone.EXILED.equals(game.getState().getZone(card.getId()))) {
            return true;
        }
        // Allow the card to be played until it leaves that exile zone.
        ContinuousEffect effect = new UnstableAmuletPlayEffect(exileId);
        effect.setTargetPointer(new FixedTarget(card.getMainCard(), game));
        game.addEffect(effect, source);
        // Clean the exile Zone from other cards, that can no longer be played.
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return true;
        }
        Set<Card> inExileZone = exileZone.getCards(game);
        for (Card cardInExile : inExileZone) {
            if (cardInExile.getMainCard().getId().equals(card.getMainCard().getId())) {
                continue;
            }
            game.getExile().moveToMainExileZone(cardInExile, game);
        }
        return true;
    }
}

class UnstableAmuletPlayEffect extends AsThoughEffectImpl {

    // The exile zone the card should be in for the effect to work.
    private final UUID exileId;

    UnstableAmuletPlayEffect(UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.exileId = exileId;
    }

    private UnstableAmuletPlayEffect(final UnstableAmuletPlayEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public UnstableAmuletPlayEffect copy() {
        return new UnstableAmuletPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card mainTargetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (mainTargetCard == null) {
            this.discard();
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || !exileZone.contains(mainTargetCard.getId())) {
            // Clean the Continuous effect if the target card is no longer in the exile zone
            this.discard();
            return false;
        }
        Card objectCard = game.getCard(objectId);
        if (objectCard == null) {
            return false;
        }
        return mainTargetCard.getId().equals(objectCard.getMainCard().getId()) // using main card to work with split/mdfc/adventures
                && affectedControllerId.equals(source.getControllerId());
    }
}
