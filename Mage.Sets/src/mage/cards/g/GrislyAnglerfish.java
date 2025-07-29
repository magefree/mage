package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GrislyAnglerfish extends CardImpl {

    public GrislyAnglerfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // {6}: Creatures your opponents control attack this turn if able.
        this.addAbility(new SimpleActivatedAbility(new AttacksIfAbleAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{6}")));
    }

    private GrislyAnglerfish(final GrislyAnglerfish card) {
        super(card);
    }

    @Override
    public GrislyAnglerfish copy() {
        return new GrislyAnglerfish(this);
    }
}
