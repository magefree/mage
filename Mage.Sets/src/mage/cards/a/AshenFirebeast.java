
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class AshenFirebeast extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public AshenFirebeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {1}{R}: Ashen Firebeast deals 1 damage to each creature without flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter), new ManaCostsImpl<>("{1}{R}")));
    }

    private AshenFirebeast(final AshenFirebeast card) {
        super(card);
    }

    @Override
    public AshenFirebeast copy() {
        return new AshenFirebeast(this);
    }
}
