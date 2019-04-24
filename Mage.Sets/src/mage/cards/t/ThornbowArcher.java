
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ThornbowArcher extends CardImpl {

    public ThornbowArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Thornbow Archer attacks, each opponent who doesn't control an Elf loses 1 life.
        this.addAbility(new AttacksTriggeredAbility(new ThornbowArcherEffect(), false));
    }

    public ThornbowArcher(final ThornbowArcher card) {
        super(card);
    }

    @Override
    public ThornbowArcher copy() {
        return new ThornbowArcher(this);
    }
}

class ThornbowArcherEffect extends OneShotEffect {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public ThornbowArcherEffect() {
        super(Outcome.LoseLife);
        this.staticText = "each opponent who doesn't control an Elf loses 1 life";
    }

    public ThornbowArcherEffect(final ThornbowArcherEffect effect) {
        super(effect);
    }

    @Override
    public ThornbowArcherEffect copy() {
        return new ThornbowArcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    if (game.getBattlefield().countAll(filter, opponentId, game) == 0) {
                        opponent.loseLife(1, game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
