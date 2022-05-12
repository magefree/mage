
package mage.cards.s;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Will
 */
public final class SamutTheTested extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and/or planeswalker cards");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
    }

    public SamutTheTested(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAMUT);

        this.setStartingLoyalty(4);

        // +1: Up to one target creature gains double strike until end of turn.
        Effect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn);
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Samut, the Tested deals 2 damage divided as you choose among one or two targets.
        effect = new DamageMultiEffect(2);
        ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetAnyTargetAmount(2));
        this.addAbility(ability);

        // -7: Search your library for up to two creature and/or planeswalker cards, put them onto the battlefield, then shuffle your library.
        effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter), false, true);
        ability = new LoyaltyAbility(effect, -7);
        this.addAbility(ability);
    }

    private SamutTheTested(final SamutTheTested card) {
        super(card);
    }

    @Override
    public SamutTheTested copy() {
        return new SamutTheTested(this);
    }
}
