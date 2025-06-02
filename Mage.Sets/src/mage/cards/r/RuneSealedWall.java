package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuneSealedWall extends CardImpl {

    public RuneSealedWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new TapSourceCost()));
    }

    private RuneSealedWall(final RuneSealedWall card) {
        super(card);
    }

    @Override
    public RuneSealedWall copy() {
        return new RuneSealedWall(this);
    }
}
