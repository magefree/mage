package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.OmenCard;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Jmlundeen
 */
public final class StormshriekFeral extends OmenCard {

    public StormshriekFeral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{R}", "Flush Out", "{1}{R}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}{R}: This creature gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 0 , Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));

        // Flush Out
        // Discard a card. If you do, draw two cards.
        this.getSpellCard().getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                null, new DiscardCardCost(), false
        ));
        this.finalizeOmen();
    }

    private StormshriekFeral(final StormshriekFeral card) {
        super(card);
    }

    @Override
    public StormshriekFeral copy() {
        return new StormshriekFeral(this);
    }
}
