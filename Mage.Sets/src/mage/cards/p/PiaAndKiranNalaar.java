
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class PiaAndKiranNalaar extends CardImpl {

    public PiaAndKiranNalaar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Pia and Kiran Nalaar enters the battlefield, create two 1/1 colorless Thopter artifact creature tokens with flying.
        Effect effect = new CreateTokenEffect(new ThopterColorlessToken(), 2);
        effect.setText("create two 1/1 colorless Thopter artifact creature tokens with flying");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // {2}{R}, Sacrifice an artifact: Pia and Kiran Nalaar deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent("an artifact"), true)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PiaAndKiranNalaar(final PiaAndKiranNalaar card) {
        super(card);
    }

    @Override
    public PiaAndKiranNalaar copy() {
        return new PiaAndKiranNalaar(this);
    }
}
