package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class TheAesirEscapeValhalla extends CardImpl {

    public TheAesirEscapeValhalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile a permanent card from your graveyard. You gain life equal to its mana value.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheAesirEscapeValhallaOneEffect());

        // II -- Put a number of +1/+1 counters on target creature you control equal to the mana value of the exiled card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new TheAesirEscapeValhallaTwoEffect(),
                new TargetControlledCreaturePermanent());

        // III -- Return The Aesir Escape Valhalla and the exiled card to their owner's hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheAesirEscapeValhallaThreeEffect());

        this.addAbility(sagaAbility);
    }

    private TheAesirEscapeValhalla(final TheAesirEscapeValhalla card) {
        super(card);
    }

    @Override
    public TheAesirEscapeValhalla copy() {
        return new TheAesirEscapeValhalla(this);
    }
}

class TheAesirEscapeValhallaOneEffect extends OneShotEffect {

    TheAesirEscapeValhallaOneEffect() {
        super(Outcome.Benefit);
        staticText = "Exile a permanent card from your graveyard. You gain life equal to its mana value.";
    }

    private TheAesirEscapeValhallaOneEffect(final TheAesirEscapeValhallaOneEffect effect) {
        super(effect);
    }

    @Override
    public TheAesirEscapeValhallaOneEffect copy() {
        return new TheAesirEscapeValhallaOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInGraveyard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_PERMANENT);
        controller.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source);
            MageObject sourceObject = source.getSourceObject(game);
            String exileName = sourceObject != null ? sourceObject.getName() : "";
            controller.moveCardsToExile(card, source, game, false, exileId, exileName);
            controller.gainLife(card.getManaValue(), game, source);
        }
        return true;
    }
}

class TheAesirEscapeValhallaTwoEffect extends OneShotEffect {

    TheAesirEscapeValhallaTwoEffect() {
        super(Outcome.Neutral);
        staticText = "Put a number of +1/+1 counters on target creature you control equal to the mana value of the exiled card";
    }

    private TheAesirEscapeValhallaTwoEffect(final TheAesirEscapeValhallaTwoEffect effect) {
        super(effect);
    }

    @Override
    public TheAesirEscapeValhallaTwoEffect copy() {
        return new TheAesirEscapeValhallaTwoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        int mv = 0;
        for (Card card : exileZone.getCards(game)) {
            mv += card.getManaValue();
        }
        UUID uuid = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(uuid);
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(mv), source.getControllerId(), source, game);
            game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts "
                    + mv + " +1/+1 counters on " + permanent.getLogName());
        }
        return true;
    }
}

class TheAesirEscapeValhallaThreeEffect extends OneShotEffect {

    TheAesirEscapeValhallaThreeEffect() {
        super(Outcome.Neutral);
        staticText = "Return {this} and the exiled card to their owner's hand.";
    }

    private TheAesirEscapeValhallaThreeEffect(final TheAesirEscapeValhallaThreeEffect effect) {
        super(effect);
    }

    @Override
    public TheAesirEscapeValhallaThreeEffect copy() {
        return new TheAesirEscapeValhallaThreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        if (sourcePermanent != null) {
            exileZone.add(sourcePermanent);
        }
        controller.moveCards(exileZone, Zone.HAND, source, game);
        return true;
    }

}
