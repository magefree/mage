
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class DarksteelJuggernaut extends CardImpl {

    public DarksteelJuggernaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Darksteel Juggernaut's power and toughness are each equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent("artifacts you control")))
        ));

        // Darksteel Juggernaut attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private DarksteelJuggernaut(final DarksteelJuggernaut card) {
        super(card);
    }

    @Override
    public DarksteelJuggernaut copy() {
        return new DarksteelJuggernaut(this);
    }

}
