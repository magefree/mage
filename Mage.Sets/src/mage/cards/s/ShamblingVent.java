
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author fireshoes
 */
public final class ShamblingVent extends CardImpl {

    public ShamblingVent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Shambling Vent enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {1}{W}{B}: Shambling Vent becomes a 2/3 white and black Elemental creature with lifelink until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new ShamblingVentToken(), "land", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}{B}")));
    }

    private ShamblingVent(final ShamblingVent card) {
        super(card);
    }

    @Override
    public ShamblingVent copy() {
        return new ShamblingVent(this);
    }
}

class ShamblingVentToken extends TokenImpl {

    public ShamblingVentToken() {
        super("", "2/3 white and black Elemental creature with lifelink");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setWhite(true);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(3);
        addAbility(LifelinkAbility.getInstance());
    }
    public ShamblingVentToken(final ShamblingVentToken token) {
        super(token);
    }

    public ShamblingVentToken copy() {
        return new ShamblingVentToken(this);
    }
}