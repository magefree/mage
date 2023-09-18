package mage.cards.m;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeetingOfTheFive extends CardImpl {

    public MeetingOfTheFive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}{B}{R}{G}");

        // Exile the top ten cards of your library. You may cast spells with exactly three colors from among them this turn. Add {W}{W}{U}{U}{B}{B}{R}{R}{G}{G}. Spend this mana only to cast spells with exactly three colors.
        this.getSpellAbility().addEffect(new MeetingOfTheFiveExileEffect());
        this.getSpellAbility().addEffect(new AddConditionalManaEffect(new Mana(
                2, 2, 2, 2, 2, 0, 0, 0
        ), new MeetingOfTheFiveManaBuilder()));
    }

    private MeetingOfTheFive(final MeetingOfTheFive card) {
        super(card);
    }

    @Override
    public MeetingOfTheFive copy() {
        return new MeetingOfTheFive(this);
    }
}

class MeetingOfTheFiveExileEffect extends OneShotEffect {

    MeetingOfTheFiveExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top ten cards of your library. You may cast " +
                "spells with exactly three colors from among them this turn";
    }

    private MeetingOfTheFiveExileEffect(final MeetingOfTheFiveExileEffect effect) {
        super(effect);
    }

    @Override
    public MeetingOfTheFiveExileEffect copy() {
        return new MeetingOfTheFiveExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            game.addEffect(new MeetingOfTheFiveCastEffect(game, card), source);
        }
        return true;
    }
}

class MeetingOfTheFiveCastEffect extends CanPlayCardControllerEffect {

    MeetingOfTheFiveCastEffect(Game game, Card card) {
        super(game, card.getId(), card.getZoneChangeCounter(game), Duration.EndOfTurn);
    }

    private MeetingOfTheFiveCastEffect(final MeetingOfTheFiveCastEffect effect) {
        super(effect);
    }

    @Override
    public MeetingOfTheFiveCastEffect copy() {
        return new MeetingOfTheFiveCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return super.applies(sourceId, source, affectedControllerId, game)
                && game.getCard(sourceId).getColor(game).getColorCount() == 3;
    }
}

class MeetingOfTheFiveManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new MeetingOfTheFiveConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast spells with exactly three colors";
    }
}

class MeetingOfTheFiveConditionalMana extends ConditionalMana {

    public MeetingOfTheFiveConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast spells with exactly three colors";
        addCondition(new MeetingOfTheFiveManaCondition());
    }
}

class MeetingOfTheFiveManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source);
        return object != null && object.getColor(game).getColorCount() == 3;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
