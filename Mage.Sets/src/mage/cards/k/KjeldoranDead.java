
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Quercitron
 */
public final class KjeldoranDead extends CardImpl {

    public KjeldoranDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Kjeldoran Dead enters the battlefield, sacrifice a creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, null));
        this.addAbility(ability);
        // {B}: Regenerate Kjeldoran Dead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private KjeldoranDead(final KjeldoranDead card) {
        super(card);
    }

    @Override
    public KjeldoranDead copy() {
        return new KjeldoranDead(this);
    }
}
