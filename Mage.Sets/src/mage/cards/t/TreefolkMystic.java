package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyAllAttachedToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class TreefolkMystic extends CardImpl {

    public TreefolkMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Treefolk Mystic blocks or becomes blocked by a creature, destroy all Auras attached to that creature.
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                new DestroyAllAttachedToTargetEffect(StaticFilters.FILTER_PERMANENT_AURAS, "that creature")
        ));
    }

    private TreefolkMystic(final TreefolkMystic card) {
        super(card);
    }

    @Override
    public TreefolkMystic copy() {
        return new TreefolkMystic(this);
    }
}
