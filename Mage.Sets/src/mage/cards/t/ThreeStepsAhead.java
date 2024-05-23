package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreeStepsAhead extends CardImpl {

    public ThreeStepsAhead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1}{U} -- Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().withFirstModeCost(new ManaCostsImpl<>("{1}{U}"));

        // + {3} -- Create a token that's a copy of target artifact or creature you control.
        this.getSpellAbility().addMode(new Mode(new CreateTokenCopyTargetEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE))
                .withCost(new GenericManaCost(3)));

        // + {2} -- Draw two cards, then discard a card.
        this.getSpellAbility().addMode(new Mode(new DrawDiscardControllerEffect(2, 1))
                .withCost(new GenericManaCost(2)));
    }

    private ThreeStepsAhead(final ThreeStepsAhead card) {
        super(card);
    }

    @Override
    public ThreeStepsAhead copy() {
        return new ThreeStepsAhead(this);
    }
}
