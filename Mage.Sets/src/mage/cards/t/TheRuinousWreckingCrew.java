package mage.cards.t;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class TheRuinousWreckingCrew extends CardImpl {

    public TheRuinousWreckingCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // The Ruinous Wrecking Crew enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
            new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // When The Ruinous Wrecking Crew enters, choose up to X --
        // * Discard a card, then draw a card.
        // * Target opponent loses 2 life.
        // * Destroy target token.
        // * Each player sacrifices a creature of their choice.
        this.addAbility(new TheRuinousWreckingCrewTriggeredAbility());
    }

    private TheRuinousWreckingCrew(final TheRuinousWreckingCrew card) {
        super(card);
    }

    @Override
    public TheRuinousWreckingCrew copy() {
        return new TheRuinousWreckingCrew(this);
    }
}

class TheRuinousWreckingCrewTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    TheRuinousWreckingCrewTriggeredAbility() {
        super(new DiscardControllerEffect(1));
        this.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        this.getModes().setChooseText("choose up to X");
        this.getModes().setMinModes(0);

        this.addMode(new Mode(new LoseLifeTargetEffect(2)).addTarget(new TargetOpponent()));
        this.addMode(new Mode(new DestroyTargetEffect("destroy target token"))
            .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_TOKEN)));
        this.addMode(new Mode(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE)));
    }

    private TheRuinousWreckingCrewTriggeredAbility(final TheRuinousWreckingCrewTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        this.getModes().setMaxModes(Math.min(4, CardUtil.getSourceCostsTag(game, this, "X", 0)));
        return true;
    }

    @Override
    public TheRuinousWreckingCrewTriggeredAbility copy() {
        return new TheRuinousWreckingCrewTriggeredAbility(this);
    }
}
