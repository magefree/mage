
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.GreenManaAbility;
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
public final class DromokaMonument extends CardImpl {

    public DromokaMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        
        // {4}{G}{W}: Dromoka Monument becomes a 4/4 green and white Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect
            (new DromokaMonumentToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{G}{W}")));
    }

    private DromokaMonument(final DromokaMonument card) {
        super(card);
    }

    @Override
    public DromokaMonument copy() {
        return new DromokaMonument(this);
    }
    
    private static class DromokaMonumentToken extends TokenImpl {
        DromokaMonumentToken() {
            super("", "4/4 green and white Dragon artifact creature with flying");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setGreen(true);
            color.setWhite(true);
            this.subtype.add(SubType.DRAGON);
            power = new MageInt(4);
            toughness = new MageInt(4);
            this.addAbility(FlyingAbility.getInstance());
        }
        public DromokaMonumentToken(final DromokaMonumentToken token) {
            super(token);
        }

        public DromokaMonumentToken copy() {
            return new DromokaMonumentToken(this);
        }
    }    
}
