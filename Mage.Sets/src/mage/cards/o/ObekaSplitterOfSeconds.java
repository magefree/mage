package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.turn.TurnMod;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ObekaSplitterOfSeconds extends CardImpl {

    public ObekaSplitterOfSeconds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Obeka, Splitter of Seconds deals combat damage to a player, you get that many additional upkeep steps after this phase.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ObekaSplitterOfSecondsEffect(),
                false, true
        ));

    }

    private ObekaSplitterOfSeconds(final ObekaSplitterOfSeconds card) {
        super(card);
    }

    @Override
    public ObekaSplitterOfSeconds copy() {
        return new ObekaSplitterOfSeconds(this);
    }
}

class ObekaSplitterOfSecondsEffect extends OneShotEffect {

    ObekaSplitterOfSecondsEffect() {
        super(Outcome.Neutral);
        staticText = "you get that many additional upkeep steps after this phase";
    }

    private ObekaSplitterOfSecondsEffect(final ObekaSplitterOfSecondsEffect effect) {
        super(effect);
    }

    @Override
    public ObekaSplitterOfSecondsEffect copy() {
        return new ObekaSplitterOfSecondsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int extraSteps = SavedDamageValue.MANY.calculate(game, source, this);
        if (extraSteps <= 0) {
            return false;
        }
        for (int i = 0; i < extraSteps; ++i) {
            game.getState().getTurnMods().add(
                    new TurnMod(source.getControllerId())
                            .withExtraStepInExtraPhase(PhaseStep.UPKEEP, TurnPhase.BEGINNING)
            );
        }
        return true;
    }

}