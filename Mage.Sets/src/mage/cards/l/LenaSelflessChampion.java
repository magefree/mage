package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author TheElk801
 */
public final class LenaSelflessChampion extends CardImpl {

    public LenaSelflessChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Lena, Selfless Champion enters the battlefield, create a 1/1 white Soldier creature token for each nontoken creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(
                        new SoldierToken(),
                        new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN)
                ).setText("create a 1/1 white Soldier creature token "
                        + "for each nontoken creature you control")
        ));

        // Sacrifice Lena: Creatures you control with power less than Lena's power gain indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new LenaSelflessChampionEffect(),
                new SacrificeSourceCost()
        ));
    }

    private LenaSelflessChampion(final LenaSelflessChampion card) {
        super(card);
    }

    @Override
    public LenaSelflessChampion copy() {
        return new LenaSelflessChampion(this);
    }
}

class LenaSelflessChampionEffect extends OneShotEffect {

    public LenaSelflessChampionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Creatures you control with power less than "
                + "{this}'s power gain indestructible until end of turn";
    }

    public LenaSelflessChampionEffect(final LenaSelflessChampionEffect effect) {
        super(effect);
    }

    @Override
    public LenaSelflessChampionEffect copy() {
        return new LenaSelflessChampionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, permanent.getPower().getValue()));
        game.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(),
                Duration.EndOfTurn, filter
        ), source);
        return true;
    }
}
