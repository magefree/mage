package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class KraulSwarm extends CardImpl {

    public KraulSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{B}, Discard a creature card: Return Kraul Swarm from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new DiscardTargetCost(
                new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A)
        ));
        this.addAbility(ability);
    }

    private KraulSwarm(final KraulSwarm card) {
        super(card);
    }

    @Override
    public KraulSwarm copy() {
        return new KraulSwarm(this);
    }
}
