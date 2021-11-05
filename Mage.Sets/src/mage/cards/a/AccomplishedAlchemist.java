package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ControllerGotLifeCount;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AccomplishedAlchemist extends CardImpl {

    public AccomplishedAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add X mana of any one color, where X is the amount of life you gained this turn.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), ControllerGotLifeCount.instance, new TapSourceCost(), "Add X mana " +
                "of any one color, where X is the amount of life you gained this turn", true
        ).addHint(ControllerGotLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private AccomplishedAlchemist(final AccomplishedAlchemist card) {
        super(card);
    }

    @Override
    public AccomplishedAlchemist copy() {
        return new AccomplishedAlchemist(this);
    }
}
