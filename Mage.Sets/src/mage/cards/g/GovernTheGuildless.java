
package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorOrColorsTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Wehk
 */
public final class GovernTheGuildless extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("monocolored creature");

    static {
        filter.add(MonocoloredPredicate.instance);
    }

    public GovernTheGuildless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");

        // Gain control of target monocolored creature.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Forecast - {1}{U}, Reveal Govern the Guildless from your hand: Target creature becomes the color or colors of your choice until end of turn.
        ForecastAbility ability = new ForecastAbility(new BecomesColorOrColorsTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GovernTheGuildless(final GovernTheGuildless card) {
        super(card);
    }

    @Override
    public GovernTheGuildless copy() {
        return new GovernTheGuildless(this);
    }
}
