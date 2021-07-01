package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisplacerBeast extends CardImpl {

    public DisplacerBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Displacer Beast enters the battlefield, venture into the dungeon.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VentureIntoTheDungeonEffect()));

        // Displacement — {3}{U}: Return Displacer Beast to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(
                new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{3}{U}")
        ).withFlavorWord("Displacement"));
    }

    private DisplacerBeast(final DisplacerBeast card) {
        super(card);
    }

    @Override
    public DisplacerBeast copy() {
        return new DisplacerBeast(this);
    }
}
