package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.StoneBoulderToken;
import mage.target.common.TargetAnyTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StoneGiantOfHighPass extends CardImpl {

    public StoneGiantOfHighPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever this creature enters or attacks, create a 3/1 colorless Wall artifact creature token with defender named Stone Boulder.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new StoneBoulderToken())
        ));

        // {2}{R}, Sacrifice an artifact: This creature deals 4 damage to any target.
        Ability ability = new SimpleActivatedAbility(
            new DamageTargetEffect(4),
            new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private StoneGiantOfHighPass(final StoneGiantOfHighPass card) {
        super(card);
    }

    @Override
    public StoneGiantOfHighPass copy() {
        return new StoneGiantOfHighPass(this);
    }
}
