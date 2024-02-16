package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WhipVine extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with flying blocked by {this}");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
    }

    public WhipVine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // You may choose not to untap Whip Vine during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Tap target creature with flying blocked by Whip Vine. That creature doesn't untap during its controller's untap step for as long as Whip Vine remains tapped.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private WhipVine(final WhipVine card) {
        super(card);
    }

    @Override
    public WhipVine copy() {
        return new WhipVine(this);
    }
}
