
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class SkarrgGoliath extends CardImpl {

    public SkarrgGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

       
        //Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Bloodrush - {5}{G}{G}, Discard Skarrg Goliath: Target attacking creature gets +9/+9 and gains trample until end of turn.
        Ability ability = new BloodrushAbility("{5}{G}{G}", new BoostTargetEffect(9,9, Duration.EndOfTurn)
                .setText("target attacking creature gets +9/+9"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private SkarrgGoliath(final SkarrgGoliath card) {
        super(card);
    }

    @Override
    public SkarrgGoliath copy() {
        return new SkarrgGoliath(this);
    }
}
