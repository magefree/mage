
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Songstitcher extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Songstitcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}: Prevent all combat damage that would be dealt this turn by target attacking creature with flying.
        Ability ability = new SimpleActivatedAbility(new PreventDamageByTargetEffect(Duration.EndOfTurn, true), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private Songstitcher(final Songstitcher card) {
        super(card);
    }

    @Override
    public Songstitcher copy() {
        return new Songstitcher(this);
    }
}
