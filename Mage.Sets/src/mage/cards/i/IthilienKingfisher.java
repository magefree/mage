package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IthilienKingfisher extends CardImpl {

    public IthilienKingfisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ithilien Kingfisher dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private IthilienKingfisher(final IthilienKingfisher card) {
        super(card);
    }

    @Override
    public IthilienKingfisher copy() {
        return new IthilienKingfisher(this);
    }
}
