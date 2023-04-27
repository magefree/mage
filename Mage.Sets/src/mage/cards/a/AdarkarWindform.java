
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AdarkarWindform extends CardImpl {

    public AdarkarWindform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{S}: Target creature loses flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilityTargetEffect(
            FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{S}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AdarkarWindform(final AdarkarWindform card) {
        super(card);
    }

    @Override
    public AdarkarWindform copy() {
        return new AdarkarWindform(this);
    }
}
