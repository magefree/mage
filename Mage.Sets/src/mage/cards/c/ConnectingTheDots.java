package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author Cguy7777
 */
public final class ConnectingTheDots extends CardImpl {

    public ConnectingTheDots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever a creature you control attacks, exile the top card of your library face down.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new ExileCardsFromTopOfLibraryControllerEffect(1, true, true, true)));

        // {1}{R}, Discard your hand, Sacrifice Connecting the Dots: Put all cards exiled with Connecting the Dots into their owners' hands.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromExileForSourceEffect(Zone.HAND)
                        .withText(true, true, true),
                new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new DiscardHandCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ConnectingTheDots(final ConnectingTheDots card) {
        super(card);
    }

    @Override
    public ConnectingTheDots copy() {
        return new ConnectingTheDots(this);
    }
}
