
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class ArmoredGuardian extends CardImpl {

    public ArmoredGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {1}{W}{W}: Target creature you control gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainProtectionFromColorTargetEffect(Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{W}{W}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        // {1}{U}{U}: Armored Guardian gains shroud until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ShroudAbility.getInstance(), Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{U}{U}")));
    }

    private ArmoredGuardian(final ArmoredGuardian card) {
        super(card);
    }

    @Override
    public ArmoredGuardian copy() {
        return new ArmoredGuardian(this);
    }
}
