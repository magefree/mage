
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class StoicEphemera extends CardImpl {

    public StoicEphemera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Stoic Ephemera blocks, sacrifice it at end of combat.
        this.addAbility(
                new BlocksSourceTriggeredAbility(
                        new CreateDelayedTriggeredAbilityEffect(
                                new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect())
                        ).setText("sacrifice it at end of combat")
                ).setTriggerPhrase("When {this} blocks, ")
        );
    }

    private StoicEphemera(final StoicEphemera card) {
        super(card);
    }

    @Override
    public StoicEphemera copy() {
        return new StoicEphemera(this);
    }
}
