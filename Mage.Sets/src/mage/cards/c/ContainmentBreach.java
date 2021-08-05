package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Pest11GainLifeToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContainmentBreach extends CardImpl {

    public ContainmentBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        this.subtype.add(SubType.LESSON);

        // Destroy target artifact or enchantment. If its mana value is 2 or less, create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        this.getSpellAbility().addEffect(new ContainmentBreachEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private ContainmentBreach(final ContainmentBreach card) {
        super(card);
    }

    @Override
    public ContainmentBreach copy() {
        return new ContainmentBreach(this);
    }
}

class ContainmentBreachEffect extends OneShotEffect {

    ContainmentBreachEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target artifact or enchantment. If its mana value is 2 or less, " +
                "create a 1/1 black and green Pest creature token with \"When this creature dies, you gain 1 life.\"";
    }

    private ContainmentBreachEffect(final ContainmentBreachEffect effect) {
        super(effect);
    }

    @Override
    public ContainmentBreachEffect copy() {
        return new ContainmentBreachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game, false);
        if (permanent.getManaValue() <= 2) {
            new Pest11GainLifeToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }
}
