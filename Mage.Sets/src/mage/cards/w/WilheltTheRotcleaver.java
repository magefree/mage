package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DecayedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WilheltTheRotcleaver extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ZOMBIE, "another Zombie you control");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.ZOMBIE, "a Zombie");

    static {
        filter.add(Predicates.not(new AbilityPredicate(DecayedAbility.class)));
    }

    public WilheltTheRotcleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another Zombie you control dies, if it didn't have decayed, create a 2/2 black Zombie creature token with decayed.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new ZombieDecayedToken())
                        .setText("if it didn't have decayed, create a 2/2 black Zombie creature token with decayed"),
                false, filter
        ));

        // At the beginning of your end step, you may sacrifice a Zombie. If you do, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(filter2))
        ), TargetController.YOU, false));
    }

    private WilheltTheRotcleaver(final WilheltTheRotcleaver card) {
        super(card);
    }

    @Override
    public WilheltTheRotcleaver copy() {
        return new WilheltTheRotcleaver(this);
    }
}
