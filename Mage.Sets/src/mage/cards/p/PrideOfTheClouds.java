
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TokenImpl;
/**
 *
 * @author fireshoes
 */
public final class PrideOfTheClouds extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature with flying on the battlefield");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }
    
    public PrideOfTheClouds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Pride of the Clouds gets +1/+1 for each other creature with flying on the battlefield.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));

        // Forecast - {2}{W}{U}, Reveal Pride of the Clouds from your hand: Create a 1/1 white and blue Bird creature token with flying.
        this.addAbility(new ForecastAbility(new CreateTokenEffect(new BirdToken()), new ManaCostsImpl("{2}{W}{U}")));
    }

    private PrideOfTheClouds(final PrideOfTheClouds card) {
        super(card);
    }

    @Override
    public PrideOfTheClouds copy() {
        return new PrideOfTheClouds(this);
    }

    private static class BirdToken extends TokenImpl {

        public BirdToken() {
            super("Bird Token", "1/1 white and blue Bird creature token with flying");
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            color.setBlue(true);
            subtype.add(SubType.BIRD);
            power = new MageInt(1);
            toughness = new MageInt(1);
            addAbility(FlyingAbility.getInstance());
        }
        public BirdToken(final BirdToken token) {
            super(token);
        }

        public BirdToken copy() {
            return new BirdToken(this);
        }
    }
}
