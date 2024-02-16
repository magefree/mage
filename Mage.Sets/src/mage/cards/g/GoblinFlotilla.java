package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;

/**
 *
 * @author MarcoMarin
 */
public final class GoblinFlotilla extends CardImpl {

    public GoblinFlotilla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // At the beginning of each combat, unless you pay {R}, whenever Goblin Flotilla blocks or becomes blocked by a creature this combat, that creature gains first strike until end of turn.
        Effect effect = new DoUnlessControllerPaysEffect(
                new GainAbilitySourceEffect(
                        new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(),
                                        Duration.EndOfTurn,
                                        "Blocks or Blocked by Goblin Flotilla"),
                                false),
                        Duration.EndOfCombat),
                new ManaCostsImpl<>("{R}"),
                "Pay Goblin Flotilla combat effect?"
        );
        effect.setText("unless you pay {R}, whenever {this} blocks or becomes blocked by a creature this combat, that creature gains first strike until end of turn.");
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                effect,
                TargetController.ANY,
                false
        ));

    }

    private GoblinFlotilla(final GoblinFlotilla card) {
        super(card);
    }

    @Override
    public GoblinFlotilla copy() {
        return new GoblinFlotilla(this);
    }
}
