package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class Lhurgoyf extends CardImpl {

    public Lhurgoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Lhurgoyf's power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LhurgoyfEffect()));
    }

    private Lhurgoyf(final Lhurgoyf card) {
        super(card);
    }

    @Override
    public Lhurgoyf copy() {
        return new Lhurgoyf(this);
    }
}

class LhurgoyfEffect extends ContinuousEffectImpl {

    public LhurgoyfEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of creature cards in all graveyards and its toughness is equal to that number plus 1";
    }

    public LhurgoyfEffect(final LhurgoyfEffect effect) {
        super(effect);
    }

    @Override
    public LhurgoyfEffect copy() {
        return new LhurgoyfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject mageObject = game.getObject(source);
            if (mageObject != null) {
                int number = 0;
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        number += player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
                    }
                }

                mageObject.getPower().setValue(number);
                mageObject.getToughness().setValue(number + 1);
                return true;

            }
        }

        return false;
    }

}
