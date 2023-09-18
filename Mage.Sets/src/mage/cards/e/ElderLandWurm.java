
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class ElderLandWurm extends CardImpl {

    public ElderLandWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Elder Land Wurm blocks, it loses defender.
        this.addAbility(
                new BlocksSourceTriggeredAbility(
                        new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.Custom).setText("it loses defender")
                ).setTriggerPhrase("When {this} blocks, ")
        );
    }

    private ElderLandWurm(final ElderLandWurm card) {
        super(card);
    }

    @Override
    public ElderLandWurm copy() {
        return new ElderLandWurm(this);
    }
}
