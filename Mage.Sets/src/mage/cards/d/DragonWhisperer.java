
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.DragonToken;

/**
 *
 * @author fireshoes
 */
public final class DragonWhisperer extends CardImpl {

    public DragonWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}: Dragon Whisperer gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), 
                        Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
        
        // {1}{R}: Dragon Whisperer get +1/+0 until end of turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{R}")));
        
        // <Formidable</i> &mdash; {4}{R}{R}: Create a 4/4 red Dragon creature token with flying. Activate this ability only if creatures you control have total power 8 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new CreateTokenEffect(new DragonToken()), 
                new ManaCostsImpl<>("{4}{R}{R}"),
                FormidableCondition.instance);
        ability.setAbilityWord(AbilityWord.FORMIDABLE);        
        this.addAbility(ability);
    }

    private DragonWhisperer(final DragonWhisperer card) {
        super(card);
    }

    @Override
    public DragonWhisperer copy() {
        return new DragonWhisperer(this);
    }
}
