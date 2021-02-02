
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CaterwaulingBoggart extends CardImpl {

    private static final FilterPermanent filterGoblin = new FilterControlledCreaturePermanent("Goblin");
    private static final FilterPermanent filterElemental = new FilterControlledCreaturePermanent("Elemental");

    static {
        filterGoblin.add(SubType.GOBLIN.getPredicate());
        filterElemental.add(SubType.ELEMENTAL.getPredicate());
    }

    public CaterwaulingBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each Goblin you control has menace. (They can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new MenaceAbility(),
                Duration.WhileOnBattlefield, filterGoblin,
                "Each Goblin you control has menace. (They can't be blocked except by two or more creatures.)")));

        // Each Elemental you control has menace. (They can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new MenaceAbility(),
                Duration.WhileOnBattlefield, filterElemental,
                "Each Elemental you control has menace. (They can't be blocked except by two or more creatures.)")));
    }

    private CaterwaulingBoggart(final CaterwaulingBoggart card) {
        super(card);
    }

    @Override
    public CaterwaulingBoggart copy() {
        return new CaterwaulingBoggart(this);
    }
}
