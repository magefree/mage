package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author DominionSpy
 */
public final class RakdosPatronOfChaos extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland, nontoken permanents");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public RakdosPatronOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, target opponent may sacrifice two nonland, nontoken permanents. If they don't, you draw two cards.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                        new DrawCardSourceControllerEffect(2),
                        new SacrificeTargetCost(2, filter))
                        .setText("target opponent may sacrifice two nonland, nontoken permanents. " +
                                "If they don't, you draw two cards."),
                TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RakdosPatronOfChaos(final RakdosPatronOfChaos card) {
        super(card);
    }

    @Override
    public RakdosPatronOfChaos copy() {
        return new RakdosPatronOfChaos(this);
    }
}
