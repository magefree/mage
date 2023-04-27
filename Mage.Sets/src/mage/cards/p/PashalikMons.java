package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PashalikMons extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.GOBLIN, "Goblin you control");
    private static final FilterControlledPermanent filter2
            = new FilterControlledCreaturePermanent(SubType.GOBLIN, "a Goblin");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PashalikMons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Pashalik Mons or another Goblin you control dies, Pashalik Mons deals 1 damage to any target.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(
                new DamageTargetEffect(1), false, filter
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {3}{R}, Sacrifice a Goblin: Create two 1/1 red Goblin creature tokens.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new GoblinToken(), 2), new ManaCostsImpl<>("{3}{R}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    private PashalikMons(final PashalikMons card) {
        super(card);
    }

    @Override
    public PashalikMons copy() {
        return new PashalikMons(this);
    }
}
