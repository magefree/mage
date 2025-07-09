package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeastToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SawtuskDemolisher extends CardImpl {

    public SawtuskDemolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {3}{G}
        this.addAbility(new MutateAbility(this, "{3}{G}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature mutates, destroy target noncreature permanent. Its controller creates a 3/3 green Beast creature token.
        Ability ability = new MutatesSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new CreateTokenControllerTargetEffect(new BeastToken()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE));
        this.addAbility(ability);
    }

    private SawtuskDemolisher(final SawtuskDemolisher card) {
        super(card);
    }

    @Override
    public SawtuskDemolisher copy() {
        return new SawtuskDemolisher(this);
    }
}
