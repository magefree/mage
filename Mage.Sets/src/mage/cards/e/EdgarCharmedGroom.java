package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgarCharmedGroom extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VAMPIRE, "Vampires");

    public EdgarCharmedGroom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.e.EdgarMarkovsCoffin.class;

        // Other Vampires you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // When Edgar, Charmed Groom dies, return it to the battlefield transformed under its owner's control.
        this.addAbility(new DiesSourceTriggeredAbility(new EdgarCharmedGroomEffect()));
    }

    private EdgarCharmedGroom(final EdgarCharmedGroom card) {
        super(card);
    }

    @Override
    public EdgarCharmedGroom copy() {
        return new EdgarCharmedGroom(this);
    }
}

class EdgarCharmedGroomEffect extends OneShotEffect {

    EdgarCharmedGroomEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield transformed under its owner's control";
    }

    private EdgarCharmedGroomEffect(final EdgarCharmedGroomEffect effect) {
        super(effect);
    }

    @Override
    public EdgarCharmedGroomEffect copy() {
        return new EdgarCharmedGroomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card)) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
