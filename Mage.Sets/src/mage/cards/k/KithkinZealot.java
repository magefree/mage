
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class KithkinZealot extends CardImpl {

    public KithkinZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Kithkin Zealot enters the battlefield, you gain 1 life for each black and/or red permanent target opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KithkinZealotEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private KithkinZealot(final KithkinZealot card) {
        super(card);
    }

    @Override
    public KithkinZealot copy() {
        return new KithkinZealot(this);
    }
}

class KithkinZealotEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public KithkinZealotEffect() {
        super(Outcome.Neutral);
        this.staticText = "you gain 1 life for each black and/or red permanent target opponent controls";
    }

    private KithkinZealotEffect(final KithkinZealotEffect effect) {
        super(effect);
    }

    @Override
    public KithkinZealotEffect copy() {
        return new KithkinZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));

        if (you!= null && opponent != null) {
            int amount = game.getBattlefield().countAll(filter, opponent.getId(), game);
            you.gainLife(amount, game, source);
            return true;            
        }
        return false;
    }
}
