
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SilentSkimmer extends CardImpl {

    public SilentSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Silent Skimmer attacks, defending player loses 2 life.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeDefendingPlayerEffect(2, true), false));
    }

    private SilentSkimmer(final SilentSkimmer card) {
        super(card);
    }

    @Override
    public SilentSkimmer copy() {
        return new SilentSkimmer(this);
    }
}
