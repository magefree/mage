
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class MagisterSphinx extends CardImpl {

    public MagisterSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{W}{U}{B}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Magister Sphinx enters the battlefield, target player's life total becomes 10.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MagisterSphinxEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    private MagisterSphinx(final MagisterSphinx card) {
        super(card);
    }

    @Override
    public MagisterSphinx copy() {
        return new MagisterSphinx(this);
    }
}

class MagisterSphinxEffect extends OneShotEffect {

    MagisterSphinxEffect() {
        super(Outcome.Detriment);
        staticText = "target player's life total becomes 10";
    }

    private MagisterSphinxEffect(final MagisterSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.setLife(10, game, source);
            return true;
        }
        return false;
    }

    @Override
    public MagisterSphinxEffect copy() {
        return new MagisterSphinxEffect(this);
    }

}