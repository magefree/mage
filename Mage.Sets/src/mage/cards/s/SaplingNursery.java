package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreefolkReachToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaplingNursery extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Treefolk and Forests");

    static {
        filter.add(Predicates.or(
                SubType.TREEFOLK.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public SaplingNursery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{G}{G}");

        // Affinity for Forests
        this.addAbility(new AffinityAbility(AffinityType.FORESTS));

        // Landfall -- Whenever a land you control enters, create a 3/4 green Treefolk creature token with reach.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new TreefolkReachToken())));

        // {1}{G}, Exile this enchantment: Treefolk and Forests you control gain indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private SaplingNursery(final SaplingNursery card) {
        super(card);
    }

    @Override
    public SaplingNursery copy() {
        return new SaplingNursery(this);
    }
}
