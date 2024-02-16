package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public class OrcishBowmasters extends CardImpl {

    public OrcishBowmasters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.addSubType(SubType.ORC);
        this.addSubType(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Orcish Bowmasters enters the battlefield and whenever an opponent draws a card except the first one they
        // draw in each of their draw steps, Orcish Bowmasters deals 1 damage to any target. Then amass Orcs 1.
        TriggeredAbility triggeredAbility =
                new OrTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, "{this}"),
                    new EntersBattlefieldTriggeredAbility(null, false),
                    new OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility(Zone.BATTLEFIELD, null, false)
                ).setTriggerPhrase("When {this} enters the battlefield and whenever an opponent draws a card " +
                    "except the first one they draw in each of their draw steps, ");
        triggeredAbility.addTarget(new TargetAnyTarget());

        Effect amass = new AmassEffect(1, SubType.ORC);
        amass.setText("Then amass Orcs 1.");
        triggeredAbility.addEffect(amass);

        this.addAbility(triggeredAbility);
    };

    private OrcishBowmasters(final OrcishBowmasters card) {
        super(card);
    }

    @Override
    public OrcishBowmasters copy() {
        return new OrcishBowmasters(this);
    }

}
