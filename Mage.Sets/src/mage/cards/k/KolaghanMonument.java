
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
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
public final class KolaghanMonument extends CardImpl {

    public KolaghanMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
        
        // {4}{B}{R}: Kolaghan Monument becomes a 4/4 black and red Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect
            (new KolaghanMonumentToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{4}{B}{R}")));
    }

    private KolaghanMonument(final KolaghanMonument card) {
        super(card);
    }

    @Override
    public KolaghanMonument copy() {
        return new KolaghanMonument(this);
    }
    
    private class KolaghanMonumentToken extends TokenImpl {
        KolaghanMonumentToken() {
            super("", "4/4 black and red Dragon artifact creature with flying");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlack(true);
            color.setRed(true);
            this.subtype.add(SubType.DRAGON);
            power = new MageInt(4);
            toughness = new MageInt(4);
            this.addAbility(FlyingAbility.getInstance());
        }
        public KolaghanMonumentToken(final KolaghanMonumentToken token) {
            super(token);
        }

        public KolaghanMonumentToken copy() {
            return new KolaghanMonumentToken(this);
        }
    }      
}
