
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author spjspj
 */
public final class PaperTiger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures named Rock Lobster");

    static {
        filter.add(new NamePredicate("Rock Lobster"));
    }

    public PaperTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        //  Creatures named Rock Lobster can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private PaperTiger(final PaperTiger card) {
        super(card);
    }

    @Override
    public PaperTiger copy() {
        return new PaperTiger(this);
    }
}
