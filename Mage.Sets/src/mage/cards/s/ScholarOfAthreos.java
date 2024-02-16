package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ScholarOfAthreos extends CardImpl {

    public ScholarOfAthreos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {2}{B}: Each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeOpponentsYouGainLifeLostEffect(1), new ManaCostsImpl<>("{2}{B}")));
    }

    private ScholarOfAthreos(final ScholarOfAthreos card) {
        super(card);
    }

    @Override
    public ScholarOfAthreos copy() {
        return new ScholarOfAthreos(this);
    }
}
