
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Juggernaut extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Walls");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public Juggernaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Juggernaut attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Juggernaut can't be blocked by Walls.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private Juggernaut(final Juggernaut card) {
        super(card);
    }

    @Override
    public Juggernaut copy() {
        return new Juggernaut(this);
    }

}
