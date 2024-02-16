
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Plopman
 */
public final class AvariceTotem extends CardImpl {

    private static final String rule = "Exchange control of {this} and target nonland permanent";
    public AvariceTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {5}: Exchange control of Avarice Totem and target nonland permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExchangeControlTargetEffect(Duration.EndOfGame, rule, true), new ManaCostsImpl<>("{5}"));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private AvariceTotem(final AvariceTotem card) {
        super(card);
    }

    @Override
    public AvariceTotem copy() {
        return new AvariceTotem(this);
    }
}
