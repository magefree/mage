package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class DefiantBloodlord extends CardImpl {

    public DefiantBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you gain life, target opponent loses that much life.
        Ability ability = new GainLifeControllerTriggeredAbility(new LoseLifeTargetEffect(SavedGainedLifeValue.MUCH), false, true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DefiantBloodlord(final DefiantBloodlord card) {
        super(card);
    }

    @Override
    public DefiantBloodlord copy() {
        return new DefiantBloodlord(this);
    }
}
