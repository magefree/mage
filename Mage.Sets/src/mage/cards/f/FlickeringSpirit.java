package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FlickeringSpirit extends CardImpl {

    public FlickeringSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}: Exile Flickering Spirit, then return it to the battlefield under its owner's control.
        this.addAbility(new SimpleActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD), new ManaCostsImpl<>("{3}{W}")
        ));
    }

    private FlickeringSpirit(final FlickeringSpirit card) {
        super(card);
    }

    @Override
    public FlickeringSpirit copy() {
        return new FlickeringSpirit(this);
    }
}
