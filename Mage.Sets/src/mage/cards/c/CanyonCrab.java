package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.HaventCastSpellFromHandThisTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CanyonCrab extends CardImpl {

    public CanyonCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // {1}{U}: Canyon Crab gets +2/-2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, -2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{U}")
        ));

        // At the beginning of your end step, if you haven't cast a spell from your hand this turn, draw a card, then discard a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1),
                TargetController.YOU, HaventCastSpellFromHandThisTurnCondition.instance, false
        ).addHint(HaventCastSpellFromHandThisTurnCondition.hint));
    }

    private CanyonCrab(final CanyonCrab card) {
        super(card);
    }

    @Override
    public CanyonCrab copy() {
        return new CanyonCrab(this);
    }
}
