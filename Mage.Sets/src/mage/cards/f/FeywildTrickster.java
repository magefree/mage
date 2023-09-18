package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FaerieDragonToken;

/**
 *
 * @author weirddan455
 */
public final class FeywildTrickster extends CardImpl {

    public FeywildTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you roll one or more dice, create a 1/1 blue Faerie Dragon creature token with flying.
        this.addAbility(new OneOrMoreDiceRolledTriggeredAbility(new CreateTokenEffect(new FaerieDragonToken())));
    }

    private FeywildTrickster(final FeywildTrickster card) {
        super(card);
    }

    @Override
    public FeywildTrickster copy() {
        return new FeywildTrickster(this);
    }
}
