
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class JoragaAuxiliary extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public JoragaAuxiliary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {4}{G}{W}: Support 2. (Put a +1/+1 counter on each of up to two other target creatures.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SupportEffect(this, 2, true), new ManaCostsImpl<>("{4}{G}{W}"));
        ability.addTarget(new TargetCreaturePermanent(0, 2, filter, false));
        this.addAbility(ability);
    }

    private JoragaAuxiliary(final JoragaAuxiliary card) {
        super(card);
    }

    @Override
    public JoragaAuxiliary copy() {
        return new JoragaAuxiliary(this);
    }
}
