
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class DroidCommando extends CardImpl {

    public DroidCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Droid Commando dies, target player loses 2 life and you gain 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private DroidCommando(final DroidCommando card) {
        super(card);
    }

    @Override
    public DroidCommando copy() {
        return new DroidCommando(this);
    }
}
