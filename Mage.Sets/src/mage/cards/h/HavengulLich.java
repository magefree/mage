package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author BetaSteward
 */
public final class HavengulLich extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card in a graveyard");

    public HavengulLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}: You may cast target creature card in a graveyard this turn. When you cast that card this turn, Havengul Lich gains all activated abilities of that card until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HavengulLichPlayEffect(), new ManaCostsImpl<>("{1}"));
        ability.addEffect(new HavengulLichPlayedEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

    }

    private HavengulLich(final HavengulLich card) {
        super(card);
    }

    @Override
    public HavengulLich copy() {
        return new HavengulLich(this);
    }
}

//allow card in graveyard to be played
class HavengulLichPlayEffect extends AsThoughEffectImpl {

    public HavengulLichPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast target creature card in a graveyard this turn";
    }

    public HavengulLichPlayEffect(final HavengulLichPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HavengulLichPlayEffect copy() {
        return new HavengulLichPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            return targetId.equals(objectId)
                    && source.isControlledBy(affectedControllerId)
                    && Zone.GRAVEYARD == game.getState().getZone(objectId);
        } else {
            // the target card has changed zone meanwhile, so the effect is no longer needed
            discard();
            return false;
        }
    }
}

//create delayed triggered ability to watch for card being played
class HavengulLichPlayedEffect extends OneShotEffect {

    public HavengulLichPlayedEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public HavengulLichPlayedEffect(final HavengulLichPlayedEffect effect) {
        super(effect);
        staticText = "When you cast that card this turn, {this} gains all activated abilities of that card until end of turn";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility ability = new HavengulLichDelayedTriggeredAbility(getTargetPointer().getFirst(game, source));
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }

    @Override
    public HavengulLichPlayedEffect copy() {
        return new HavengulLichPlayedEffect(this);
    }

}

// when card is played create continuous effect
class HavengulLichDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID cardId;

    public HavengulLichDelayedTriggeredAbility(UUID cardId) {
        super(new HavengulLichEffect(cardId), Duration.EndOfTurn);
        this.cardId = cardId;
    }

    public HavengulLichDelayedTriggeredAbility(HavengulLichDelayedTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(cardId);
    }

    @Override
    public HavengulLichDelayedTriggeredAbility copy() {
        return new HavengulLichDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast that card this turn, {this} gains all activated abilities of that card until end of turn.";
    }
}

// copy activated abilities of card
class HavengulLichEffect extends ContinuousEffectImpl {

    private final UUID cardId;

    public HavengulLichEffect(UUID cardId) {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.cardId = cardId;
    }

    public HavengulLichEffect(final HavengulLichEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public HavengulLichEffect copy() {
        return new HavengulLichEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(cardId);
        if (permanent != null && card != null) {
            for (ActivatedAbility ability : card.getAbilities(game).getActivatedAbilities(Zone.BATTLEFIELD)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return false;
    }
}
