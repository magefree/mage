package mage.cards.c;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConduitOfWorlds extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland permanent card");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public ConduitOfWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}{G}");

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(new PlayLandsFromGraveyardControllerEffect()));

        // {T}: Choose target nonland permanent card in your graveyard. If you haven't cast a spell this turn, you may cast that card. If you do, you can't cast additional spells this turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ConduitOfWorldsEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability, new SpellsCastWatcher());
    }

    private ConduitOfWorlds(final ConduitOfWorlds card) {
        super(card);
    }

    @Override
    public ConduitOfWorlds copy() {
        return new ConduitOfWorlds(this);
    }
}

class ConduitOfWorldsEffect extends OneShotEffect {

    ConduitOfWorldsEffect() {
        super(Outcome.Benefit);
        staticText = "choose target nonland permanent card in your graveyard. " +
                "If you haven't cast a spell this turn, you may cast that card. " +
                "If you do, you can't cast additional spells this turn";
    }

    private ConduitOfWorldsEffect(final ConduitOfWorldsEffect effect) {
        super(effect);
    }

    @Override
    public ConduitOfWorldsEffect copy() {
        return new ConduitOfWorldsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .isEmpty()) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null || !player.chooseUse(
                Outcome.Benefit, "Cast " + card.getName() + "? (You still pay the costs)", source, game
        )) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        boolean wasCast = player.cast(
                player.chooseAbilityForCast(card, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        if (!wasCast) {
            return false;
        }
        game.addEffect(new ConduitOfWorldsCantCastEffect(), source);
        return true;
    }
}

class ConduitOfWorldsCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public ConduitOfWorldsCantCastEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
    }

    public ConduitOfWorldsCantCastEffect(final ConduitOfWorldsCantCastEffect effect) {
        super(effect);
    }

    @Override
    public ConduitOfWorldsCantCastEffect copy() {
        return new ConduitOfWorldsCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
