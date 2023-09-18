package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class KnightOfDusk extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature blocking {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public KnightOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}{B}: Destroy target creature blocking Knight of Dusk.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{B}{B}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KnightOfDusk(final KnightOfDusk card) {
        super(card);
    }

    @Override
    public KnightOfDusk copy() {
        return new KnightOfDusk(this);
    }
}
