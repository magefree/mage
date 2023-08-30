package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noahg
 */
public final class ConjurersBan extends CardImpl {

    public ConjurersBan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Choose a card name. Until your next turn, spells with the chosen name can’t be cast and lands with the chosen name can’t be played.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new ConjurersBanEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ConjurersBan(final ConjurersBan card) {
        super(card);
    }

    @Override
    public ConjurersBan copy() {
        return new ConjurersBan(this);
    }
}

class ConjurersBanEffect extends ContinuousRuleModifyingEffectImpl {

    ConjurersBanEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Detriment, true, false);
        this.staticText = "Until your next turn, spells with the chosen name can't be cast and lands with the chosen name can't be played";
    }

    private ConjurersBanEffect(final ConjurersBanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ConjurersBanEffect copy() {
        return new ConjurersBanEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL || event.getType() == GameEvent.EventType.PLAY_LAND) {
            MageObject object = game.getObject(event.getSourceId());
            String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
            return CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        String playerName = game.getPlayer(source.getControllerId()).getName();
        if (cardName == null || playerName == null || source.getSourceObject(game) == null) {
            return super.getInfoMessage(source, event, game);
        }
        return "Until " + playerName + "'s next turn, spells named " + cardName + " can't be cast and lands named " + cardName + " can't be played (" + source.getSourceObject(game).getIdName() + ").";
    }
}
