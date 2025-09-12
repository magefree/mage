package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ShardPhoenix extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public ShardPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice Shard Phoenix: Shard Phoenix deals 2 damage to each creature without flying.
        this.addAbility(new SimpleActivatedAbility(new DamageAllEffect(2, "it", filter), new SacrificeSourceCost()));

        // {R}{R}{R}: Return Shard Phoenix from your graveyard to your hand. Activate this ability only during your upkeep.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{R}{R}{R}"), IsStepCondition.getMyUpkeep()
        ));
    }

    private ShardPhoenix(final ShardPhoenix card) {
        super(card);
    }

    @Override
    public ShardPhoenix copy() {
        return new ShardPhoenix(this);
    }
}
