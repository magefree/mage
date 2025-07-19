package mage.cards.s;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.LanderToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScroungeForEternity extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or Spacecraft card with mana value 5 or less from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public ScroungeForEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Return target creature or Spacecraft card with mana value 5 or less from your graveyard to the battlefield. Then create a Lander token.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new LanderToken()).concatBy("Then"));
    }

    private ScroungeForEternity(final ScroungeForEternity card) {
        super(card);
    }

    @Override
    public ScroungeForEternity copy() {
        return new ScroungeForEternity(this);
    }
}
