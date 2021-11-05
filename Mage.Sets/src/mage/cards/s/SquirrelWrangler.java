package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SquirrelToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SquirrelWrangler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SQUIRREL, "Squirrel creatures");

    public SquirrelWrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}, Sacrifice a land: Create two 1/1 green Squirrel creature tokens.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SquirrelToken(), 2), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        this.addAbility(ability);

        // {1}{G}, Sacrifice a land: Squirrel creatures get +1/+1 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        this.addAbility(ability);

    }

    private SquirrelWrangler(final SquirrelWrangler card) {
        super(card);
    }

    @Override
    public SquirrelWrangler copy() {
        return new SquirrelWrangler(this);
    }
}
