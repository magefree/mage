package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinArchitectOfLaw extends CardImpl {

    public DovinArchitectOfLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOVIN);
        this.setStartingLoyalty(5);

        // +1: You gain 2 life and draw a card.
        Ability ability = new LoyaltyAbility(new GainLifeEffect(2), 1);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // -1: Tap target creature. It doesn't untap during its controller's next untap step.
        ability = new LoyaltyAbility(new TapTargetEffect(), -1);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -9: Tap all permanents target opponent controls. That player skips their next untap step.
        ability = new LoyaltyAbility(new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_PERMANENT), -9);
        ability.addEffect(new SkipNextPlayerUntapStepEffect("That player"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DovinArchitectOfLaw(final DovinArchitectOfLaw card) {
        super(card);
    }

    @Override
    public DovinArchitectOfLaw copy() {
        return new DovinArchitectOfLaw(this);
    }
}
