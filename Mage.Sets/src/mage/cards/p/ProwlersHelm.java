
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class ProwlersHelm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Walls");
    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    public ProwlersHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can't be blocked except by Walls.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, filter, AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(2), false));
    }

    private ProwlersHelm(final ProwlersHelm card) {
        super(card);
    }

    @Override
    public ProwlersHelm copy() {
        return new ProwlersHelm(this);
    }
}
