package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThirdPathSavant extends CardImpl {

    public ThirdPathSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {7}: Draw two cards.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2), new GenericManaCost(7)
        ));
    }

    private ThirdPathSavant(final ThirdPathSavant card) {
        super(card);
    }

    @Override
    public ThirdPathSavant copy() {
        return new ThirdPathSavant(this);
    }
}
