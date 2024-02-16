
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author KholdFuzion
 *
 */
public final class ZombieMaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Zombie creatures");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public ZombieMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Zombie creatures have swampwalk.
        Effect effect = new GainAbilityAllEffect(new SwampwalkAbility(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("Other Zombie creatures have swampwalk. <i>(They can't be blocked as long as defending player controls a Swamp.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Other Zombies have "{B}: Regenerate this permanent."
        effect = new GainAbilityAllEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")), Duration.WhileOnBattlefield, filter, true);
        effect.setText("Other Zombies have \"{B}: Regenerate this permanent.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private ZombieMaster(final ZombieMaster card) {
        super(card);
    }

    @Override
    public ZombieMaster copy() {
        return new ZombieMaster(this);
    }
}
