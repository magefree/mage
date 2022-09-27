package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssaultIntercessor extends CardImpl {

    public AssaultIntercessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Chainsword -- Whenever a creature an opponent controls dies, that player loses 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AssaultIntercessorEffect(), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ).withFlavorWord("Chainsword"));
    }

    private AssaultIntercessor(final AssaultIntercessor card) {
        super(card);
    }

    @Override
    public AssaultIntercessor copy() {
        return new AssaultIntercessor(this);
    }
}

class AssaultIntercessorEffect extends OneShotEffect {

    AssaultIntercessorEffect() {
        super(Outcome.Benefit);
        staticText = "that player loses 2 life";
    }

    private AssaultIntercessorEffect(final AssaultIntercessorEffect effect) {
        super(effect);
    }

    @Override
    public AssaultIntercessorEffect copy() {
        return new AssaultIntercessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getValue("creatureDied"))
                .filter(Objects::nonNull)
                .map(Permanent.class::cast)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.loseLife(2, game, source, false) > 0)
                .orElse(false);
    }
}
