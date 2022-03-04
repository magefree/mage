package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Graxiplon extends CardImpl {

    public Graxiplon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Graxiplon can't be blocked unless defending player controls three or more creatures that share a creature type.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), GraxiplonCondition.instance, "{this} can't be blocked " +
                "unless defending player controls three or more creatures that share a creature type"
        )));
    }

    private Graxiplon(final Graxiplon card) {
        super(card);
    }

    @Override
    public Graxiplon copy() {
        return new Graxiplon(this);
    }
}

enum GraxiplonCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        return player == null || GreatestSharedCreatureTypeCount.getValue(
                player.getId(), source.getSourceId(), game
        ) < 3;
    }
}