package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeyserLeaper extends CardImpl {

    public GeyserLeaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Waterbend {4}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new WaterbendCost(4)
        ));
    }

    private GeyserLeaper(final GeyserLeaper card) {
        super(card);
    }

    @Override
    public GeyserLeaper copy() {
        return new GeyserLeaper(this);
    }
}
