
package mage.cards.t;

import java.util.UUID;
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
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

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
        FilterPermanent filter = new FilterPermanent("artifact or creature");
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), +2);
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -3: You gain 2 life and draw two cards.
        ability = new LoyaltyAbility(new GainLifeEffect(2), -3);
        ability.addEffect(new DrawCardSourceControllerEffect(2).setText("and draw two cards"));
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
