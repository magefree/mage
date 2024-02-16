package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author dustinconrad
 */
public final class CinderWall extends CardImpl {

    public CinderWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // When Cinder Wall blocks, destroy it at end of combat.
        this.addAbility(
                new BlocksSourceTriggeredAbility(
                        new CreateDelayedTriggeredAbilityEffect(
                                new AtTheEndOfCombatDelayedTriggeredAbility(new DestroySourceEffect())
                        ).setText("destroy it at end of combat")
                ).setTriggerPhrase("When {this} blocks, ")
        );
    }

    private CinderWall(final CinderWall card) {
        super(card);
    }

    @Override
    public CinderWall copy() {
        return new CinderWall(this);
    }
}
