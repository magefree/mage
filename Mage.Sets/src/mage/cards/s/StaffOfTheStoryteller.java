package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801, isaacwatson
 */
public final class StaffOfTheStoryteller extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public StaffOfTheStoryteller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // When Staff of the Story Teller enters the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken(), 1)));

        // Whenever one or more creature tokens enter the battlefield, put a story counter on Staff of the Storyteller.
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
                new AddCountersSourceEffect(CounterType.STORY.createInstance()),
                filter,
                TargetController.YOU)
        );

        // {W}, {T}, Remove a story counter from Staff of the Storyteller: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{W}"));

        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.STORY.createInstance()));
        this.addAbility(ability);
    }

    private StaffOfTheStoryteller(final StaffOfTheStoryteller card) { super(card); }
    
    @Override
    public StaffOfTheStoryteller copy() {
        return new StaffOfTheStoryteller(this);
    }
}
