package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ScreechingBat extends TransformingDoubleFacedCard {

    public ScreechingBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BAT}, "{2}{B}",
                "Stalking Vampire",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "B"
        );

        // Screeching Bat
        this.getLeftHalfCard().setPT(2, 2);

        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Screeching Bat.
        Ability transformAbility = new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect(),
                new ManaCostsImpl<>("{2}{B}{B}")
        ));
        this.getLeftHalfCard().addAbility(transformAbility);

        // Stalking Vampire
        this.getRightHalfCard().setPT(5, 5);

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Screeching Bat.
        this.getRightHalfCard().addAbility(transformAbility.copy());
    }

    private ScreechingBat(final ScreechingBat card) {
        super(card);
    }

    @Override
    public ScreechingBat copy() {
        return new ScreechingBat(this);
    }
}
