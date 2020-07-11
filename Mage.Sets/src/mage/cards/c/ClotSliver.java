
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ClotSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Slivers");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public ClotSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect("this permanent"), new GenericManaCost(2)), Duration.WhileOnBattlefield, filter, false)));
    }

    public ClotSliver(final ClotSliver card) {
        super(card);
    }

    @Override
    public ClotSliver copy() {
        return new ClotSliver(this);
    }
}
