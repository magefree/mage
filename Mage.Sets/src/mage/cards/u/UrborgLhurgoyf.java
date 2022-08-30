package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrborgLhurgoyf extends CardImpl {

    public UrborgLhurgoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.LHURGOYF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Kicker {U} and/or {B}
        KickerAbility kickerAbility = new KickerAbility("{U}");
        kickerAbility.addKickerCost("{B}");
        this.addAbility(kickerAbility);

        // As Urborg Lhurgoyf enters the battlefield, mill three cards for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new MillCardsControllerEffect(MultikickerCount.instance), null,
                "As {this} enters the battlefield, " +
                        "mill three cards for each time it was kicked.", ""
        ));

        // Urborg Lhurgoyf's power is equal to the number of creature cards in your graveyard and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new UrborgLhurgoyfEffect()));
    }

    private UrborgLhurgoyf(final UrborgLhurgoyf card) {
        super(card);
    }

    @Override
    public UrborgLhurgoyf copy() {
        return new UrborgLhurgoyf(this);
    }
}

class UrborgLhurgoyfEffect extends ContinuousEffectImpl {

    public UrborgLhurgoyfEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of creature cards in your graveyard and its toughness is equal to that number plus 1";
    }

    public UrborgLhurgoyfEffect(final UrborgLhurgoyfEffect effect) {
        super(effect);
    }

    @Override
    public UrborgLhurgoyfEffect copy() {
        return new UrborgLhurgoyfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        int number = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        sourceObject.getPower().setValue(number);
        sourceObject.getToughness().setValue(number + 1);
        return true;
    }
}
