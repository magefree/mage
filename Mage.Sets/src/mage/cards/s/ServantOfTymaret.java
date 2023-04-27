
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class ServantOfTymaret extends CardImpl {

    public ServantOfTymaret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // <i>Inspired</i> &mdash; Whenever Servant of Tymaret becomes untapped, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new InspiredAbility(new ServantOfTymaretEffect()));

        // {2}{B}: Regenerate Servant of Tymaret.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private ServantOfTymaret(final ServantOfTymaret card) {
        super(card);
    }

    @Override
    public ServantOfTymaret copy() {
        return new ServantOfTymaret(this);
    }
}

class ServantOfTymaretEffect extends OneShotEffect {

    public ServantOfTymaretEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    public ServantOfTymaretEffect(final ServantOfTymaretEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lostAmount = 0;
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            lostAmount += game.getPlayer(opponentId).loseLife(1, game, source, false);
        }
        game.getPlayer(source.getControllerId()).gainLife(lostAmount, game, source);
        return true;
    }

    @Override
    public ServantOfTymaretEffect copy() {
        return new ServantOfTymaretEffect(this);
    }

}
