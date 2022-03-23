package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class TinderWall extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature it's blocking");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
    }

    public TinderWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Sacrifice Tinder Wall: Add {R}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new SacrificeSourceCost()));

        // {R}, Sacrifice Tinder Wall: Tinder Wall deals 2 damage to target creature it's blocking.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TinderWall(final TinderWall card) {
        super(card);
    }

    @Override
    public TinderWall copy() {
        return new TinderWall(this);
    }
}
