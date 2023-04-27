
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ScarwoodHag extends CardImpl {

    public ScarwoodHag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HAG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}{G}{G}{G}, {tap}: Target creature gains forestwalk until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(new ForestwalkAbility(false), Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}{G}{G}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {tap}: Target creature loses forestwalk until end of turn.
        Effect effect = new LoseAbilityTargetEffect(new ForestwalkAbility(true), Duration.EndOfTurn);
        effect.setText("Target creature loses forestwalk until end of turn");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect,
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ScarwoodHag(final ScarwoodHag card) {
        super(card);
    }

    @Override
    public ScarwoodHag copy() {
        return new ScarwoodHag(this);
    }
}
