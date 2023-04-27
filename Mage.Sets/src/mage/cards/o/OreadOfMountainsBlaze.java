package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OreadOfMountainsBlaze extends CardImpl {

    public OreadOfMountainsBlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{R}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private OreadOfMountainsBlaze(final OreadOfMountainsBlaze card) {
        super(card);
    }

    @Override
    public OreadOfMountainsBlaze copy() {
        return new OreadOfMountainsBlaze(this);
    }
}
