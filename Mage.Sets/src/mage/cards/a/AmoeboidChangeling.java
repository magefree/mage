
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainAllCreatureTypesTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllCreatureTypesTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author EvilGeek
 */
public final class AmoeboidChangeling extends CardImpl {

    public AmoeboidChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // {tap}: Target creature gains all creature types until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAllCreatureTypesTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {tap}: Target creature loses all creature types until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAllCreatureTypesTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());

        this.addAbility(ability);
    }

    private AmoeboidChangeling(final AmoeboidChangeling card) {
        super(card);
    }

    @Override
    public AmoeboidChangeling copy() {
        return new AmoeboidChangeling(this);
    }
}
