package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class AerialCaravan extends CardImpl {

    public AerialCaravan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}{U}: Exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1).setText("Exile the top card of your library. " +
                                "Until end of turn, you may play that card. <i>(Reveal the card as you exile it.)</i>"),
                new ManaCostsImpl("{1}{U}{U}")));
    }

    private AerialCaravan(final AerialCaravan card) {
        super(card);
    }

    @Override
    public AerialCaravan copy() {
        return new AerialCaravan(this);
    }
}
