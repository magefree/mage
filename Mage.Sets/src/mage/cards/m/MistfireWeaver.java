
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MistfireWeaver extends CardImpl {

    public MistfireWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{U}")));
        // When Misfire Weaver is turned face up, target creature  you control gains hexproof until end of turn
        Effect effect = new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

    }

    private MistfireWeaver(final MistfireWeaver card) {
        super(card);
    }

    @Override
    public MistfireWeaver copy() {
        return new MistfireWeaver(this);
    }
}
