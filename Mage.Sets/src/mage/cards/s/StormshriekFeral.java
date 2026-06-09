package mage.cards.s;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class StormshriekFeral extends OmenCard {

    public StormshriekFeral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{R}",
                "Flush Out",
                new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Stormshriek Feral
        this.getLeftHalfCard().setPT(3, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // {1}{R}: This creature gets +1/+0 until end of turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 0 , Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));

        // Flush Out
        // Discard a card. If you do, draw two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                null, new DiscardCardCost(), false
        ));

        finalizeCard();
    }

    private StormshriekFeral(final StormshriekFeral card) {
        super(card);
    }

    @Override
    public StormshriekFeral copy() {
        return new StormshriekFeral(this);
    }
}
