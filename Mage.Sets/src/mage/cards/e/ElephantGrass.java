
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayManaAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Plopman
 */
public final class ElephantGrass extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Nonblack creatures");
    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("Black creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ElephantGrass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));

        // Black creatures can't attack you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouAllEffect(Duration.WhileOnBattlefield, filterBlack)));

        // Nonblack creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouUnlessPayManaAllEffect(new ManaCostsImpl<>("{2}"), false, filter)));
    }

    private ElephantGrass(final ElephantGrass card) {
        super(card);
    }

    @Override
    public ElephantGrass copy() {
        return new ElephantGrass(this);
    }
}
