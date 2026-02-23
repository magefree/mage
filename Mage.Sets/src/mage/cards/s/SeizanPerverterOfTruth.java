package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SeizanPerverterOfTruth extends CardImpl {

    public SeizanPerverterOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // At the beginning of each player's upkeep, that player loses 2 life and draws two cards.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER, new LoseLifeTargetEffect(2), false);
        ability.addEffect(new DrawCardTargetEffect(2).withTargetDescription("and"));
        this.addAbility(ability);
    }

    private SeizanPerverterOfTruth(final SeizanPerverterOfTruth card) {
        super(card);
    }

    @Override
    public SeizanPerverterOfTruth copy() {
        return new SeizanPerverterOfTruth(this);
    }

}
