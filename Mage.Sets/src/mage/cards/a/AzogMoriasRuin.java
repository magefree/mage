package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

public final class AzogMoriasRuin extends CardImpl {

    public AzogMoriasRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Azog enters, destroy up to one other target creature.
        // Its controller amasses Goblins X, where X is that creature’s power.
        // If you controlled that creature, draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AzogMoriasRuinEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private AzogMoriasRuin(final AzogMoriasRuin card) {
        super(card);
    }

    @Override
    public AzogMoriasRuin copy() {
        return new AzogMoriasRuin(this);
    }
}

class AzogMoriasRuinEffect extends OneShotEffect {

    AzogMoriasRuinEffect() {
        super(Outcome.Benefit);
        staticText = "destroy up to one other target creature. " +
                "Its controller amasses Goblins X, where X is that creature’s power. " +
                "If you controlled that creature, draw a card";
    }

    private AzogMoriasRuinEffect(final AzogMoriasRuinEffect effect) {
        super(effect);
    }

    @Override
    public AzogMoriasRuinEffect copy() {
        return new AzogMoriasRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        int xValue = permanent.getPower().getValue();
        // Destroy up to one other target creature
        permanent.destroy(source, game);
        // Its controller amasses Goblins X, where X is that creature’s power.
        AmassEffect.doAmass(player, xValue, SubType.GOBLIN, game, source);
        // If you controlled that creature, draw a card.
        if (player.equals(controller)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
