
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SoulOfTheros extends CardImpl {

    public SoulOfTheros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // {4}{W}{W}: Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.
        Effect effect1 = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect1.setText("Creatures you control get +2/+2");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect1, new ManaCostsImpl<>("{4}{W}{W}"));
        Effect effect2 = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and gain first strike");
        ability.addEffect(effect2);
        Effect effect3 = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect3.setText("and lifelink until end of turn");
        ability.addEffect(effect3);
        this.addAbility(ability);

        // {4}{W}{W}, Exile Soul of Theros from your graveyard: Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, effect1, new ManaCostsImpl<>("{4}{W}{W}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(effect2);
        ability.addEffect(effect3);
        this.addAbility(ability);
    }

    private SoulOfTheros(final SoulOfTheros card) {
        super(card);
    }

    @Override
    public SoulOfTheros copy() {
        return new SoulOfTheros(this);
    }
}
