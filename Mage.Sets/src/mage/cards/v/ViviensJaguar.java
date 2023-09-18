package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author TheElk801
 */
public final class ViviensJaguar extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(
                    SubType.VIVIEN,
                    "a Vivien planeswalker"
            );

    public ViviensJaguar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {2}{G}: Return Vivien's Jaguar from your graveyard to your hand. Activate this ability only if you control a Vivien planeswalker.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{2}{G}"),
                new PermanentsOnTheBattlefieldCondition(filter),
                "{2}{G}: Return {this} from your graveyard to your hand. "
                + "Activate only if you control a Vivien planeswalker."
        ));
    }

    private ViviensJaguar(final ViviensJaguar card) {
        super(card);
    }

    @Override
    public ViviensJaguar copy() {
        return new ViviensJaguar(this);
    }
}
