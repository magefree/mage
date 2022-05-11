
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class SkyshroudWarBeast extends CardImpl {

    public SkyshroudWarBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Skyshroud War Beast enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.BoostCreature)));

        // Skyshroud War Beast's power and toughness are each equal to the number of nonbasic lands the chosen player controls.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SkyshroudWarBeastEffect()));
    }

    private SkyshroudWarBeast(final SkyshroudWarBeast card) {
        super(card);
    }

    @Override
    public SkyshroudWarBeast copy() {
        return new SkyshroudWarBeast(this);
    }
}

class SkyshroudWarBeastEffect extends ContinuousEffectImpl {

    public SkyshroudWarBeastEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power and toughness are each equal to the number of nonbasic lands the chosen player controls";
    }

    public SkyshroudWarBeastEffect(final SkyshroudWarBeastEffect effect) {
        super(effect);
    }

    @Override
    public SkyshroudWarBeastEffect copy() {
        return new SkyshroudWarBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject target = game.getObject(source);
            if (target != null) {
                UUID playerId = (UUID) game.getState().getValue(source.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY);
                FilterLandPermanent filter = FilterLandPermanent.nonbasicLand();
                filter.add(new ControllerIdPredicate(playerId));
                int number = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
                target.getPower().setValue(number);
                target.getToughness().setValue(number);
                return true;
            }
        }
        return false;
    }
}
