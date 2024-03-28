package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Cguy7777
 */
public final class InnocuousResearcher extends CardImpl {

    public InnocuousResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Parley -- Whenever Innocuous Researcher attacks, each player reveals the top card of their library.
        // For each nonland card revealed this way, you investigate. Then each player draws a card.
        Ability parleyAbility = new AttacksTriggeredAbility(new InvestigateEffect(ParleyCount.getInstance())
                .setText("each player reveals the top card of their library. " +
                        "For each nonland card revealed this way, you investigate"));
        parleyAbility.addEffect(new DrawCardAllEffect(1).concatBy("Then"));
        this.addAbility(parleyAbility.setAbilityWord(AbilityWord.PARLEY));

        // At the beginning of your end step, you may untap all lands you control.
        // If you do, you can't cast spells until your next turn.
        Ability untapAbility = new BeginningOfEndStepTriggeredAbility(
                new UntapAllLandsControllerEffect(), TargetController.YOU, true);
        untapAbility.addEffect(new InnocuousResearcherEffect().concatBy("If you do,"));
        this.addAbility(untapAbility);
    }

    private InnocuousResearcher(final InnocuousResearcher card) {
        super(card);
    }

    @Override
    public InnocuousResearcher copy() {
        return new InnocuousResearcher(this);
    }
}

class InnocuousResearcherEffect extends ContinuousRuleModifyingEffectImpl {

    InnocuousResearcherEffect() {
        super(Duration.UntilYourNextTurn, Outcome.PreventCast);
        staticText = "you can't cast spells until your next turn";
    }

    private InnocuousResearcherEffect(final InnocuousResearcherEffect effect) {
        super(effect);
    }

    @Override
    public InnocuousResearcherEffect copy() {
        return new InnocuousResearcherEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells until your next turn (" + mageObject.getIdName() + ").";
        }
        return null;
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
