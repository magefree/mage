package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrostpeakYeti extends CardImpl {

    public FrostpeakYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{S}: Frostpeak Yeti can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{S}")
        ));
    }

    private FrostpeakYeti(final FrostpeakYeti card) {
        super(card);
    }

    @Override
    public FrostpeakYeti copy() {
        return new FrostpeakYeti(this);
    }
}
