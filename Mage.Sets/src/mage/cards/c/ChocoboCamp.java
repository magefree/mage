package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AddCounterNextSpellDelayedTriggeredAbility;
import mage.abilities.condition.common.YouControlALegendaryCreatureCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.game.permanent.token.ChocoboToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChocoboCamp extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a Bird creature spell");

    static {
        filter.add(SubType.BIRD.getPredicate());
    }

    public ChocoboCamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlALegendaryCreatureCondition.instance)
                .addHint(YouControlALegendaryCreatureCondition.getHint()));

        // {T}: Add {G}. When you next cast a Bird creature spell this turn, it enters with an additional +1/+1 counter on it.
        BasicManaAbility manaAbility = new GreenManaAbility();
        manaAbility.addEffect(new CreateDelayedTriggeredAbilityEffect(new AddCounterNextSpellDelayedTriggeredAbility(filter)));
        manaAbility.setUndoPossible(false);
        this.addAbility(manaAbility);

        // {2}{G}{G}, {T}: Create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new ChocoboToken()), new ManaCostsImpl<>("{2}{G}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ChocoboCamp(final ChocoboCamp card) {
        super(card);
    }

    @Override
    public ChocoboCamp copy() {
        return new ChocoboCamp(this);
    }
}
