package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class GrimGiganotosaurus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures other than {this}");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public GrimGiganotosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // {10}{B}{G}: Monstrosity 10. This ability costs {1} less to activate for each creature with power 4 or greater your opponents control.
        this.addAbility(new MonstrosityAbility("{10}{B}{G}", 10,
                GrimGiganotosaurusAdjuster.instance,
                "This ability costs {1} less to activate for each creature with power 4 or greater your opponents control. "));

        // When Grim Giganotosaurus becomes monstrous, destroy all artifacts and creatures other than Grim Giganotosaurus.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new DestroyAllEffect(filter)));
    }

    private GrimGiganotosaurus(final GrimGiganotosaurus card) {
        super(card);
    }

    @Override
    public GrimGiganotosaurus copy() {
        return new GrimGiganotosaurus(this);
    }
}

enum GrimGiganotosaurusAdjuster implements CostAdjuster {
    instance;

    private static final FilterCreaturePermanent filter = new FilterOpponentsCreaturePermanent("creature with power 4 or greater your opponents control");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            CardUtil.reduceCost(ability, xValue.calculate(game, ability, null));
        }
    }
}
