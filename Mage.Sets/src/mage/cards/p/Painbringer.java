
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Painbringer extends CardImpl {

    public Painbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Exile any number of cards from your graveyard: Target creature gets -X/-X until end of turn, where X is the number of cards exiled this way.
        DynamicValue X = new SignInversionDynamicValue(new GetXValue());
        Effect effect = new BoostTargetEffect(X, X, Duration.EndOfTurn);
        effect.setText("Target creature gets -X/-X until end of turn, where X is the number of cards exiled this way");
        Ability ability = new SimpleActivatedAbility(effect, new TapSourceCost());
        ability.addCost(new ExileXFromYourGraveCost(new FilterCard("any number of cards from your graveyard")));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    public Painbringer(final Painbringer card) {
        super(card);
    }

    @Override
    public Painbringer copy() {
        return new Painbringer(this);
    }
}
