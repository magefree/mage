package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExcavatedWall extends CardImpl {

    public ExcavatedWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}, {T}: Mill a card.
        Ability ability = new SimpleActivatedAbility(
                new MillCardsControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ExcavatedWall(final ExcavatedWall card) {
        super(card);
    }

    @Override
    public ExcavatedWall copy() {
        return new ExcavatedWall(this);
    }
}
