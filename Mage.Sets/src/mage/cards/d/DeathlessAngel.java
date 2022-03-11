

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DeathlessAngel extends CardImpl {

    public DeathlessAngel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {W}{W}: Target creature is indestructible this turn.
        Effect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect, new ManaCostsImpl("{W}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DeathlessAngel (final DeathlessAngel card) {
        super(card);
    }

    @Override
    public DeathlessAngel copy() {
        return new DeathlessAngel(this);
    }

}
