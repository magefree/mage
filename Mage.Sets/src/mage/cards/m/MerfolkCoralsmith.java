package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerfolkCoralsmith extends CardImpl {

    public MerfolkCoralsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}: Merfolk Coralsmith gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, -1, Duration.EndOfTurn), new GenericManaCost(1)
        ));

        // When Merfolk Coralsmith dies, scry 2.
        this.addAbility(new DiesSourceTriggeredAbility(new ScryEffect(2, false)));
    }

    private MerfolkCoralsmith(final MerfolkCoralsmith card) {
        super(card);
    }

    @Override
    public MerfolkCoralsmith copy() {
        return new MerfolkCoralsmith(this);
    }
}
