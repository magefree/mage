package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpiritBonds extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Spirit creature");
    private static final FilterControlledPermanent filterSpirit = new FilterControlledPermanent("Spirit");

    static {
        filter.add(Predicates.not(SubType.SPIRIT.getPredicate()));
        filterSpirit.add(SubType.SPIRIT.getPredicate());
    }

    public SpiritBonds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");


        // Whenever a nontoken creature enters the battlefield under your control, you may pay {W}. If you do, but a 1/1 white Spirit creature token with flying into play.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new SpiritWhiteToken()), new ManaCostsImpl<>("{W}")), StaticFilters.FILTER_CREATURE_NON_TOKEN, false));

        // {1}{W}, Sacrifice a Spirit: Target non-Spirit creature gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filterSpirit, true)));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SpiritBonds(final SpiritBonds card) {
        super(card);
    }

    @Override
    public SpiritBonds copy() {
        return new SpiritBonds(this);
    }
}
