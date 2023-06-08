package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssaultOnOsgiliath extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate()
        ));
    }

    public AssaultOnOsgiliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Amass Orcs X, then Goblins and Orcs you control gain double strike and haste until end of turn.
        this.getSpellAbility().addEffect(new AssimilateEssenceEffect());
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText(", then Goblins and Orcs you control gain double strike"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and haste until end of turn"));
    }

    private AssaultOnOsgiliath(final AssaultOnOsgiliath card) {
        super(card);
    }

    @Override
    public AssaultOnOsgiliath copy() {
        return new AssaultOnOsgiliath(this);
    }
}

class AssaultOnOsgiliathEffect extends OneShotEffect {

    AssaultOnOsgiliathEffect() {
        super(Outcome.Benefit);
        staticText = "amass Orcs X";
    }

    private AssaultOnOsgiliathEffect(final AssaultOnOsgiliathEffect effect) {
        super(effect);
    }

    @Override
    public AssaultOnOsgiliathEffect copy() {
        return new AssaultOnOsgiliathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return AmassEffect.doAmass(source.getManaCostsToPay().getX(), SubType.ORC, game, source) != null;
    }
}
