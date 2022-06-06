
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
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
 * @author Loki
 */
public final class ForbiddingWatchtower extends CardImpl {

    public ForbiddingWatchtower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Forbidding Watchtower enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{W}: Forbidding Watchtower becomes a 1/5 white Soldier creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new ForbiddingWatchtowerToken(), "land", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}")));
    }

    private ForbiddingWatchtower(final ForbiddingWatchtower card) {
        super(card);
    }

    @Override
    public ForbiddingWatchtower copy() {
        return new ForbiddingWatchtower(this);
    }
}

class ForbiddingWatchtowerToken extends TokenImpl {
    ForbiddingWatchtowerToken() {
        super("Soldier", "1/5 white Soldier creature");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SOLDIER);
        color.setWhite(true);
        
        power = new MageInt(1);
        toughness = new MageInt(5);
    }
    public ForbiddingWatchtowerToken(final ForbiddingWatchtowerToken token) {
        super(token);
    }

    public ForbiddingWatchtowerToken copy() {
        return new ForbiddingWatchtowerToken(this);
    }
}
