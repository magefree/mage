package mage.cards.v;

import mage.abilities.costs.costadjusters.CommanderManaValueAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionsOfDuplicity extends CardImpl {

    public VisionsOfDuplicity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Exchange control of two target creatures you don't control.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(
                Duration.EndOfGame, "exchange control of two target creatures you don't control"
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(
                2, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL
        ));

        // Flashback {8}{U}{U}. This spell costs {X} less to cast this way, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{U}{U}"))
                .setAbilityName("This spell costs {X} less to cast this way, where X is the greatest mana value " +
                        "of a commander you own on the battlefield or in the command zone.")
                .setCostAdjuster(CommanderManaValueAdjuster.instance));
    }

    private VisionsOfDuplicity(final VisionsOfDuplicity card) {
        super(card);
    }

    @Override
    public VisionsOfDuplicity copy() {
        return new VisionsOfDuplicity(this);
    }
}
