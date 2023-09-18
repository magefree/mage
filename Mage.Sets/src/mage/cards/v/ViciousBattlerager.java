package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViciousBattlerager extends CardImpl {

    public ViciousBattlerager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // When Vicious Battlerager enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Spiked Retribution — Whenever Vicious Battlerager becomes blocked by a creature, that creature's controller loses 5 life.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(
                new ViciousBattleragerEffect(), false
        ).withFlavorWord("Spiked Retribution"));
    }

    private ViciousBattlerager(final ViciousBattlerager card) {
        super(card);
    }

    @Override
    public ViciousBattlerager copy() {
        return new ViciousBattlerager(this);
    }
}

class ViciousBattleragerEffect extends OneShotEffect {

    ViciousBattleragerEffect() {
        super(Outcome.Benefit);
        staticText = "that creature's controller loses 5 life";
    }

    private ViciousBattleragerEffect(final ViciousBattleragerEffect effect) {
        super(effect);
    }

    @Override
    public ViciousBattleragerEffect copy() {
        return new ViciousBattleragerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(game.getPlayer(game.getControllerId(getTargetPointer().getFirst(game, source))))
                .map(player -> player.loseLife(5, game, source, false) > 0)
                .orElse(false);
    }
}
