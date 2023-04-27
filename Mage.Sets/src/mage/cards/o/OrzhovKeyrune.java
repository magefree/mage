package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrzhovKeyrune extends CardImpl {

    public OrzhovKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {W}{B}: Orzhov Keyrune becomes a 1/4 white and black Thrull artifact creature with lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new OrzhovKeyruneToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{W}{B}")));
    }

    private OrzhovKeyrune(final OrzhovKeyrune card) {
        super(card);
    }

    @Override
    public OrzhovKeyrune copy() {
        return new OrzhovKeyrune(this);
    }

    private static class OrzhovKeyruneToken extends TokenImpl {
        OrzhovKeyruneToken() {
            super("Thrull", "1/4 white and black Thrull artifact creature with lifelink");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            color.setBlack(true);
            subtype.add(SubType.THRULL);
            power = new MageInt(1);
            toughness = new MageInt(4);
            this.addAbility(LifelinkAbility.getInstance());
        }

        public OrzhovKeyruneToken(final OrzhovKeyruneToken token) {
            super(token);
        }

        public OrzhovKeyruneToken copy() {
            return new OrzhovKeyruneToken(this);
        }
    }
}
