package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Robot11Token;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MouserFoundry extends CardImpl {

    public MouserFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // When this artifact enters or leaves the battlefield, create a 1/1 colorless Robot artifact creature token.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new CreateTokenEffect(new Robot11Token()), false));

        // {4}{R}, Sacrifice this artifact: It deals 3 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new ManaCostsImpl<>("{4}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MouserFoundry(final MouserFoundry card) {
        super(card);
    }

    @Override
    public MouserFoundry copy() {
        return new MouserFoundry(this);
    }
}
