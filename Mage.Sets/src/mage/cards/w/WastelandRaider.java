package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WastelandRaider extends CardImpl {

    public WastelandRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Squad {2}
        this.addAbility(new SquadAbility(new ManaCostsImpl<>("{2}")));

        // When Wasteland Raider enters the battlefield, each player sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(
                1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        )));
    }

    private WastelandRaider(final WastelandRaider card) {
        super(card);
    }

    @Override
    public WastelandRaider copy() {
        return new WastelandRaider(this);
    }
}
