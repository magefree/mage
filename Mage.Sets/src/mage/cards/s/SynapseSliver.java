package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class SynapseSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "a Sliver");

    public SynapseSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals combat damage to a player, its controller may draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new SynapseSliverEffect(), filter, false,
                SetTargetPointer.PLAYER, true
        ));
    }

    private SynapseSliver(final SynapseSliver card) {
        super(card);
    }

    @Override
    public SynapseSliver copy() {
        return new SynapseSliver(this);
    }
}

class SynapseSliverEffect extends OneShotEffect {

    SynapseSliverEffect() {
        super(Outcome.Benefit);
        staticText = "its controller may draw a card";
    }

    private SynapseSliverEffect(final SynapseSliverEffect effect) {
        super(effect);
    }

    @Override
    public SynapseSliverEffect copy() {
        return new SynapseSliverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null
                && player.chooseUse(outcome, "Draw a card?", source, game)
                && player.drawCards(1, source, game) > 0;
    }
}
