package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class ChitteringWitch extends CardImpl {

    public ChitteringWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Chittering Witch enters the battlefield, create a number of 1/1 black Rat creature tokens equal to the number of opponents you have.
        Effect effect = new CreateTokenEffect(new RatToken(), OpponentsCount.instance);
        effect.setText("create a number of 1/1 black Rat creature tokens equal to the number of opponents you have");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // {1}{B}, Sacrifice a creature: Target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ChitteringWitch(final ChitteringWitch card) {
        super(card);
    }

    @Override
    public ChitteringWitch copy() {
        return new ChitteringWitch(this);
    }
}
