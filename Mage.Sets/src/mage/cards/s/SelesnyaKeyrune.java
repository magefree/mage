
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
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
 * @author LevelX2
 */
public final class SelesnyaKeyrune extends CardImpl {

    public SelesnyaKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {G}{W}: Selesnya Keyrune becomes a 3/3 green and white Wolf artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new SelesnyaKeyruneToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{G}{W}")));
    }

    private SelesnyaKeyrune(final SelesnyaKeyrune card) {
        super(card);
    }

    @Override
    public SelesnyaKeyrune copy() {
        return new SelesnyaKeyrune(this);
    }

    private static class SelesnyaKeyruneToken extends TokenImpl {
        SelesnyaKeyruneToken() {
            super("", "3/3 green and white Wolf artifact creature");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            color.setGreen(true);
            this.subtype.add(SubType.WOLF);
            power = new MageInt(3);
            toughness = new MageInt(3);
        }
        public SelesnyaKeyruneToken(final SelesnyaKeyruneToken token) {
            super(token);
        }

        public SelesnyaKeyruneToken copy() {
            return new SelesnyaKeyruneToken(this);
        }
    }
}
