
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class BringerOfTheRedDawn extends CardImpl {

    public BringerOfTheRedDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{R}{R}");
        this.subtype.add(SubType.BRINGER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may pay {W}{U}{B}{R}{G} rather than pay Bringer of the Red Dawn's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, you may untap target creature and gain control of it until end of turn. That creature gains haste until end of turn.
        Effect effect = new UntapTargetEffect();
        effect.setText("untap target creature");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, true);
        
        effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it until end of turn");
        ability.addEffect(effect);
        
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("That creature gains haste until end of turn");
        ability.addEffect(effect);
        
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BringerOfTheRedDawn(final BringerOfTheRedDawn card) {
        super(card);
    }

    @Override
    public BringerOfTheRedDawn copy() {
        return new BringerOfTheRedDawn(this);
    }
}
