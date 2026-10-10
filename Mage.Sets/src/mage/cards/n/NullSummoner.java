package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.effects.*;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author miesma
 */
public final class NullSummoner extends CardImpl {

    public NullSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When this creature enters, if you cast it, target opponent reveals their hand. You choose a nonland card from it. Exile that card.
        Ability entersAbility = new EntersBattlefieldTriggeredAbility(
                new NullSummonerExileEffect()
        ).withInterveningIf(CastFromEverywhereSourceCondition.instance);
        entersAbility.addTarget(new TargetOpponent());
        this.addAbility(entersAbility);

        // Threshold — As long as there are seven or more cards in your graveyard, you may cast the exiled card, and mana of any type can be spent to cast that spell.
        Ability staticThreshold = new SimpleStaticAbility(new NullSummonerCastEffect());
        staticThreshold.withFlavorWord("Threshold");
        this.addAbility(staticThreshold);
    }

    private NullSummoner(final NullSummoner card) {
        super(card);
    }

    @Override
    public NullSummoner copy() {
        return new NullSummoner(this);
    }
}

class NullSummonerExileEffect extends OneShotEffect {

    NullSummonerExileEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals their hand. You choose a nonland card from it. Exile that card.";
    }

    private NullSummonerExileEffect(final NullSummonerExileEffect effect) {
        super(effect);
    }

    @Override
    public NullSummonerExileEffect copy() {
        return new NullSummonerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getHand());
        opponent.revealCards(source, cards, game);
        if (cards.count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        target.withNotTarget(true);
        controller.choose(Outcome.Exile, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        // Needs unique Key across two different source abilities
        StringBuilder unique;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            // Will never be able to cast this one
            unique = new StringBuilder("unused");
        } else {
            unique = new StringBuilder("nullsummonereffect_");
            unique.append(permanent.getId().toString());
        }
        unique.append(permanent.getZoneChangeCounter(game));
        return controller.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(unique.toString(), game),
                CardUtil.getSourceName(game, source));
    }
}

class NullSummonerCastEffect extends AsThoughEffectImpl {

    boolean addManaEffect = false;

    NullSummonerCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as there are seven or more cards in your graveyard, you may cast the exiled card, and mana of any type can be spent to cast that spell.";
    }

    private NullSummonerCastEffect(final NullSummonerCastEffect effect) {
        super(effect);
    }

    @Override
    public NullSummonerCastEffect copy() {
        return new NullSummonerCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        StringBuilder unique = new StringBuilder("nullsummonereffect_");
        unique.append(permanent.getId().toString());
        unique.append(permanent.getZoneChangeCounter(game));
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(unique.toString(), game));
        if (exileZone != null && exileZone.contains(objectId) && ThresholdCondition.instance.apply(game, source)) {
            if (!addManaEffect) {
                // Need a FixedTarget on the Card as it moves from exile to spell zone once casting begins
                // Only add the effect once and not every time applies returns true
                ContinuousEffect effect = new NullSummonerManaEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
                addManaEffect = true;
            }
            return true;
        }
        return false;
    }


}

class NullSummonerManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    NullSummonerManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "and mana of any type can be spent to cast that spell.";
    }

    private NullSummonerManaEffect(final NullSummonerManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NullSummonerManaEffect copy() {
        return new NullSummonerManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from exile to spell the zone change counter is increased by 1 (effect must applies before and on stack, use isCheckPlayableMode?)
            return source.isControlledBy(affectedControllerId);
        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarted
            this.discard();
        }
        return false;
    }


    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

