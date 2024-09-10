package mage.cards.t;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TeferiTimebender extends CardImpl {

    public TeferiTimebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.setStartingLoyalty(5);

        // +2: Untap up to one target artifact or creature.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), +2);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE, false));
        this.addAbility(ability);

        // -3: You gain 2 life and draw two cards.
        ability = new LoyaltyAbility(new GainLifeEffect(2), -3);
        ability.addEffect(new DrawCardSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);

        // -9: Take an extra turn after this one.
        this.addAbility(new LoyaltyAbility(new AddExtraTurnControllerEffect(), -9));
    }

    private TeferiTimebender(final TeferiTimebender card) {
        super(card);
    }

    @Override
    public TeferiTimebender copy() {
        return new TeferiTimebender(this);
    }
}
