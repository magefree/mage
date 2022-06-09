
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class OjutaiMonument extends CardImpl {

    public OjutaiMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
        
        // {4}{W}{U}: Ojutai Monument becomes a 4/4 white and blue Dragon artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect
            (new OjutaiMonumentToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{4}{W}{U}")));
    }

    private OjutaiMonument(final OjutaiMonument card) {
        super(card);
    }

    @Override
    public OjutaiMonument copy() {
        return new OjutaiMonument(this);
    }
    
    private static class OjutaiMonumentToken extends TokenImpl {
        OjutaiMonumentToken() {
            super("", "4/4 white and blue Dragon artifact creature with flying");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            color.setBlue(true);
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
