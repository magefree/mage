package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.VampireDemonToken;

public class PromiseOfAclazotz extends AdventureCard {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public PromiseOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.SORCERY}, "{1}{B}", "Foul Rebirth", "{2}{B}");

        // At the beginning of your end step, you may sacrifice a non-Demon creature. If you do, populate.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new PopulateEffect(),
                new SacrificeTargetCost(filter)
        ), TargetController.YOU, false);
        this.addAbility(ability);

        // Foul Rebirth
        // Sacrifice a non-Demon creature. If you do, create a 4/3 white and black Vampire Demon creature token with flying.
        this.getSpellCard().getSpellAbility().addEffect(new DoIfCostPaid(
                new CreateTokenEffect(new VampireDemonToken()),
                new SacrificeTargetCost(filter), null, false
        ));

        this.finalizeAdventure();
    }

    private PromiseOfAclazotz(final PromiseOfAclazotz card) {
        super(card);
    }

    @Override
    public PromiseOfAclazotz copy() {
        return new PromiseOfAclazotz(this);
    }
}
