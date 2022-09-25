package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RavenousAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Zoanthrope extends CardImpl {

    public Zoanthrope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{U}{R}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Warp Blast -- When Zoanthrope enters the battlefield, it deals X damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(ManacostVariableValue.ETB, "it")
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Warp Blast"));
    }

    private Zoanthrope(final Zoanthrope card) {
        super(card);
    }

    @Override
    public Zoanthrope copy() {
        return new Zoanthrope(this);
    }
}
