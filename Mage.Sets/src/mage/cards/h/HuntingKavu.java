
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class HuntingKavu extends CardImpl {

    private static final FilterCreatureAttackingYou filter = new FilterCreatureAttackingYou("creature without flying that's attacking you");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public HuntingKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}{R}{G}, {tap}: Exile Hunting Kavu and target creature without flying that's attacking you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileSourceEffect(), new ManaCostsImpl<>("{1}{R}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ExileTargetEffect().setText("and target creature without flying that's attacking you"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private HuntingKavu(final HuntingKavu card) {
        super(card);
    }

    @Override
    public HuntingKavu copy() {
        return new HuntingKavu(this);
    }
}
