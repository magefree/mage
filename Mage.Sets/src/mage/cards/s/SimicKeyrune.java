
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author LevelX2
 */
public final class SimicKeyrune extends CardImpl {

    public SimicKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // {G}{U}: Simic Keyrune becomes a 2/3 green and blue Crab artifact creature with hexproof until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new SimicKeyruneToken(), CardType.ARTIFACT, Duration.EndOfTurn), new ManaCostsImpl<>("{G}{U}")));
    }

    private SimicKeyrune(final SimicKeyrune card) {
        super(card);
    }

    @Override
    public SimicKeyrune copy() {
        return new SimicKeyrune(this);
    }

    private static class SimicKeyruneToken extends TokenImpl {
        SimicKeyruneToken() {
            super("Crab", "2/3 green and blue Crab artifact creature with hexproof");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setGreen(true);
            color.setBlue(true);
            subtype.add(SubType.CRAB);
            power = new MageInt(2);
            toughness = new MageInt(3);
            this.addAbility(HexproofAbility.getInstance());
        }

        public SimicKeyruneToken(final SimicKeyruneToken token) {
            super(token);
        }

        public SimicKeyruneToken copy() {
            return new SimicKeyruneToken(this);
        }
    }
}
