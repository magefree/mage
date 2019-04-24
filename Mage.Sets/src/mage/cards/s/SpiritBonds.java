
package mage.cards.s;

import java.util.UUID;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SpiritBonds extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Spirit creature you control");
    private static final FilterControlledPermanent filterSpirit = new FilterControlledPermanent("Spirit");
    private static final FilterControlledCreaturePermanent filterNontoken = new FilterControlledCreaturePermanent("nontoken creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.SPIRIT)));
        filterSpirit.add(new SubtypePredicate(SubType.SPIRIT));
        filterNontoken.add(Predicates.not(new TokenPredicate()));
    }

    public SpiritBonds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Whenever a nontoken creature enters the battlefield under your control, you may pay {W}. If you do, but a 1/1 white Spirit creature token with flying into play.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new SpiritWhiteToken("M15")), new ManaCostsImpl("{W}")), filterNontoken, false));
        
        // {1}{W}, Sacrifice a Spirit: Target non-Spirit creature you control gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{1}{W}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1,1,filterSpirit, true)));
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public SpiritBonds(final SpiritBonds card) {
        super(card);
    }

    @Override
    public SpiritBonds copy() {
        return new SpiritBonds(this);
    }
}
