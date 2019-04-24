package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class AncestorDragon extends CardImpl {

    public AncestorDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more creatures you control attack, you gain 1 life for each attacking creature.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new AncestorDragonEffect(), 1));
    }

    public AncestorDragon(final AncestorDragon card) {
        super(card);
    }

    @Override
    public AncestorDragon copy() {
        return new AncestorDragon(this);
    }
}

class AncestorDragonEffect extends OneShotEffect {

    private int attackers;

    public AncestorDragonEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 1 life for each attacking creature";
    }

    public AncestorDragonEffect(final AncestorDragonEffect effect) {
        super(effect);
    }

    @Override
    public AncestorDragonEffect copy() {
        return new AncestorDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        attackers = game.getCombat().getAttackers().size();
        if (you != null) {
            you.gainLife(attackers, game, source);
            attackers = 0;
            return true;
        }
        return false;
    }
}
