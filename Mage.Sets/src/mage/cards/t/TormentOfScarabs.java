package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2, Susucr
 */
public final class TormentOfScarabs extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public TormentOfScarabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseLife));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player loses 3 life unless they sacrifice a nonland permanent or discards a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ENCHANTED, new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                        new LoseLifeTargetEffect(3),
                        new OrCost(
                                "sacrifice a nonland permanent or discard a card",
                                new SacrificeTargetCost(filter),
                                new DiscardCardCost()
                        ),
                        "Sacrifice a nonland permanent or discard a card to prevent losing 3 life?"
                ), false
        ));
    }

    private TormentOfScarabs(final TormentOfScarabs card) {
        super(card);
    }

    @Override
    public TormentOfScarabs copy() {
        return new TormentOfScarabs(this);
    }
}
