package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class KorokLiberatedRage extends CardImpl {

    public KorokLiberatedRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Battlefront -- Whenever Korok attacks, creatures you control get +X/+X until end of turn, where X is the number of opponents you attacked this turn.
        this.addAbility(new AttacksTriggeredAbility(
            new BoostControlledEffect(
                AttackedThisTurnOpponentsCount.instance,
                AttackedThisTurnOpponentsCount.instance,
                Duration.EndOfTurn
            ).setText("creatures you control get +X/+X until end of turn, where X is the number of opponents you attacked this turn"))
                .withFlavorWord("Battlefront")
                .addHint(AttackedThisTurnOpponentsCount.getHint()),
            new PlayersAttackedThisTurnWatcher()
        );
    }

    private KorokLiberatedRage(final KorokLiberatedRage card) {
        super(card);
    }

    @Override
    public KorokLiberatedRage copy() {
        return new KorokLiberatedRage(this);
    }
}
