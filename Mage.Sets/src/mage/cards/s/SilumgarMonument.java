
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class SilumgarMonument extends CardImpl {

    public SilumgarMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
        
        // {4}{U}{B}: Silumgar Monument becomes a 4/4 blue and black Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect
            (new OjutaiMonumentToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{U}{B}")));
    }

    private SilumgarMonument(final SilumgarMonument card) {
        super(card);
    }

    @Override
    public SilumgarMonument copy() {
        return new SilumgarMonument(this);
    }
    
    private static class OjutaiMonumentToken extends TokenImpl {
        OjutaiMonumentToken() {
            super("", "4/4 blue and black Dragon artifact creature with flying");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            color.setBlack(true);
            this.subtype.add(SubType.DRAGON);
            power = new MageInt(4);
            toughness = new MageInt(4);
            this.addAbility(FlyingAbility.getInstance());
        }
        public OjutaiMonumentToken(final OjutaiMonumentToken token) {
            super(token);
        }
    
        public OjutaiMonumentToken copy() {
            return new OjutaiMonumentToken(this);
        }
    }      
}
