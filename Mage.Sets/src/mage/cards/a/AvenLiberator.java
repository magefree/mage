
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AvenLiberator extends CardImpl {

    public AvenLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {3}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{W}")));
        // When Aven Liberator is turned face up, target creature you control gains protection from the color of your choice until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AvenLiberator(final AvenLiberator card) {
        super(card);
    }

    @Override
    public AvenLiberator copy() {
        return new AvenLiberator(this);
    }
}
