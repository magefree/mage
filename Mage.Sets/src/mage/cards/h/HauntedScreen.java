package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntedScreen extends CardImpl {

    public HauntedScreen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {T}, Pay 1 life: Add {G}, {U}, or {R}.
        Ability ability = new GreenManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
        ability = new BlueManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
        ability = new RedManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {7}: Put seven +1/+1 counters on Haunted Screen. It becomes a 0/0 Spirit creature in addition to its other types. Activate only once.
        ability = new ActivateOncePerGameActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(7)), new GenericManaCost(7)
        );
        ability.addEffect(new BecomesCreatureSourceEffect(new CreatureToken(
                0, 0, "0/0 Spirit creature", SubType.SPIRIT
        ), CardType.ARTIFACT, Duration.Custom).withKeepCreatureSubtypes(true)
                .setText("It becomes a 0/0 Spirit creature in addition to its other types"));
        this.addAbility(ability);
    }

    private HauntedScreen(final HauntedScreen card) {
        super(card);
    }

    @Override
    public HauntedScreen copy() {
        return new HauntedScreen(this);
    }
}
