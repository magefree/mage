package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.game.turn.UpkeepStep;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheNinthDoctor extends CardImpl {

    public TheNinthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD, SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Into the TARDIS — Whenever The Ninth Doctor becomes untapped during your untap step, you get an additional upkeep step after this step.
        TriggeredAbilityImpl ability = new InspiredAbility(new TheNinthDoctorEffect(), false, false).setTriggerPhrase("Whenever {this} becomes untapped during your untap step, ");
        ability.withFlavorWord("Into the TARDIS");
        this.addAbility(new ConditionalTriggeredAbility(ability, new IsStepCondition(PhaseStep.UNTAP), ""));
    }

    private TheNinthDoctor(final TheNinthDoctor card) {
        super(card);
    }

    @Override
    public TheNinthDoctor copy() {
        return new TheNinthDoctor(this);
    }
}

class TheNinthDoctorEffect extends OneShotEffect {

    TheNinthDoctorEffect() {
        super(Outcome.Benefit);
        this.staticText = "you get an additional upkeep step after this step";
    }

    private TheNinthDoctorEffect(final TheNinthDoctorEffect effect) {
        super(effect);
    }

    @Override
    public TheNinthDoctorEffect copy() {
        return new TheNinthDoctorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = source.getControllerId();
        if (playerId != null) {
            game.getState().getTurnMods().add(new TurnMod(playerId).withExtraStep(new UpkeepStep()));
        }
        return true;
    }
}
