
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author hanasu
 */
public final class SkitteringSkirge extends CardImpl {

    public SkitteringSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you cast a creature spell, sacrifice Skittering Skirge.
        this.addAbility(new SpellCastControllerTriggeredAbility(new SacrificeSourceEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false));
    }

    private SkitteringSkirge(final SkitteringSkirge card) {
        super(card);
    }

    @Override
    public SkitteringSkirge copy() {
        return new SkitteringSkirge(this);
    }
}
