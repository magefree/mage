package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class GoblinCratermaker extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("colorless nonland permanent");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public GoblinCratermaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice Goblin Cratermaker: Choose one —
        // • Goblin Cratermaker deals 2 damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());

        // • Destroy target colorless nonland permanent.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));

        ability.addMode(mode);
        this.addAbility(ability);
    }

    private GoblinCratermaker(final GoblinCratermaker card) {
        super(card);
    }

    @Override
    public GoblinCratermaker copy() {
        return new GoblinCratermaker(this);
    }
}
