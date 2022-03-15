
package mage.cards.k;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KariZevsExpertise extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");
    private static final FilterCard filter2 = new FilterCard("a spell with mana value 2 or less");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public KariZevsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Gain control of target creature or Vehicle until end of turn. Untap it. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));

        // You may cast a card with converted mana cost 2 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new CastFromHandForFreeEffect(filter2).concatBy("<br>"));
    }

    private KariZevsExpertise(final KariZevsExpertise card) {
        super(card);
    }

    @Override
    public KariZevsExpertise copy() {
        return new KariZevsExpertise(this);
    }
}
