
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ChromeshellCrab extends CardImpl {
    
    private static final String rule = "you may exchange control of target creature you control and target creature an opponent controls";

    public ChromeshellCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Morph {4}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{4}{U}")));
        
        // When Chromeshell Crab is turned face up, you may exchange control of target creature you control and target creature an opponent controls.
        Effect effect = new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true);
        effect.setText("exchange control of target creature you control and target creature an opponent controls");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private ChromeshellCrab(final ChromeshellCrab card) {
        super(card);
    }

    @Override
    public ChromeshellCrab copy() {
        return new ChromeshellCrab(this);
    }
}
