package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SinisterStarfish extends CardImpl {

    public SinisterStarfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.STARFISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {T}: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new TapSourceCost()));
    }

    private SinisterStarfish(final SinisterStarfish card) {
        super(card);
    }

    @Override
    public SinisterStarfish copy() {
        return new SinisterStarfish(this);
    }
}
