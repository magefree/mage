package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.JunkToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThrillKillDisciple extends CardImpl {

    public ThrillKillDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Squad--{1}, Discard a card.
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new ManaCostsImpl<>("{1}"));
        costs.add(new DiscardCardCost());
        this.addAbility(new SquadAbility(costs));

        // When Thrill-Kill Disciple dies, create a Junk token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new JunkToken())));
    }

    private ThrillKillDisciple(final ThrillKillDisciple card) {
        super(card);
    }

    @Override
    public ThrillKillDisciple copy() {
        return new ThrillKillDisciple(this);
    }
}
