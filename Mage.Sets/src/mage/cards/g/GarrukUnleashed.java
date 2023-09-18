package mage.cards.g;

import mage.abilities.LoyaltyAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.command.emblems.GarrukUnleashedEmblem;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class GarrukUnleashed extends CardImpl {

    public GarrukUnleashed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);
        this.setStartingLoyalty(4);

        // +1: Up to one target creature gets +3/+3 and gains trample until end of turn.
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn)
            .setText("up to one target creature gets +3/+3");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
            .setText("and gains trample until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −2: Create a 3/3 green Beast creature token. Then if an opponent controls more creatures than you, put a loyalty counter on Garruk, Unleashed.
        ability = new LoyaltyAbility(new CreateTokenEffect(new BeastToken()), -2);
        ability.addEffect(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()),
                new OpponentControlsMoreCondition(new FilterCreaturePermanent()))
                .setText("Then if an opponent controls more creatures than you, put a loyalty counter on {this}"));
        this.addAbility(ability);

        // −7: You get an emblem with "At the beginning of your end step, you may search your library for a creature card, put it onto the battlefield, then shuffle your library."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GarrukUnleashedEmblem()), -7));
    }

    private GarrukUnleashed(final GarrukUnleashed card) {
        super(card);
    }

    @Override
    public GarrukUnleashed copy() {
        return new GarrukUnleashed(this);
    }
}
