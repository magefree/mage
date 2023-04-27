
package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;
import mage.filter.StaticFilters;

/**
 *
 * @author ciaccona007
 */
public final class TimmyPowerGamer extends CardImpl {

    public TimmyPowerGamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.GAMER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {4}: You may put a creature card from your hand onto the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A),
                new ManaCostsImpl<>("{4}"));
        this.addAbility(ability);
    }

    private TimmyPowerGamer(final TimmyPowerGamer card) {
        super(card);
    }

    @Override
    public TimmyPowerGamer copy() {
        return new TimmyPowerGamer(this);
    }
}
