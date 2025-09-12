package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class NestingGrounds extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public NestingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Move a counter from target permanent you control onto another target permanent. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new MoveCounterTargetsEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent().withChooseHint("to remove a counter from").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filter).withChooseHint("to move a counter to").setTargetTag(2));
        this.addAbility(ability);
    }

    private NestingGrounds(final NestingGrounds card) {
        super(card);
    }

    @Override
    public NestingGrounds copy() {
        return new NestingGrounds(this);
    }
}
