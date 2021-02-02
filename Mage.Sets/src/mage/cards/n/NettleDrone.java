
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class NettleDrone extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a colorless spell");

    static {
        filterSpell.add(ColorlessPredicate.instance);
    }

    public NettleDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {T}: Nettle Drone deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost()));

        // Whenever you cast a colorless spell, untap Nettle Drone.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filterSpell, false));
    }

    private NettleDrone(final NettleDrone card) {
        super(card);
    }

    @Override
    public NettleDrone copy() {
        return new NettleDrone(this);
    }
}
