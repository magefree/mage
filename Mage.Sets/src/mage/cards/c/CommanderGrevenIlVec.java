
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class CommanderGrevenIlVec extends CardImpl {

    public CommanderGrevenIlVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Fear (This creature can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(FearAbility.getInstance());

        // When Commander Greven il-Vec enters the battlefield, sacrifice a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""), false));
    }

    private CommanderGrevenIlVec(final CommanderGrevenIlVec card) {
        super(card);
    }

    @Override
    public CommanderGrevenIlVec copy() {
        return new CommanderGrevenIlVec(this);
    }
}
