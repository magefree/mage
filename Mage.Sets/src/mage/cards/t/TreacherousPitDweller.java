package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldFromGraveyardTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class TreacherousPitDweller extends CardImpl {

    public TreacherousPitDweller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Undying
        this.addAbility(new UndyingAbility());

        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        Ability ability = new EntersBattlefieldFromGraveyardTriggeredAbility(new TreacherousPitDwellerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TreacherousPitDweller(final TreacherousPitDweller card) {
        super(card);
    }

    @Override
    public TreacherousPitDweller copy() {
        return new TreacherousPitDweller(this);
    }
}

class TreacherousPitDwellerEffect extends ContinuousEffectImpl {

    public TreacherousPitDwellerEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public TreacherousPitDwellerEffect(final TreacherousPitDwellerEffect effect) {
        super(effect);
    }

    @Override
    public TreacherousPitDwellerEffect copy() {
        return new TreacherousPitDwellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject permanent = source.getSourceObjectIfItStillExists(game); // it can also return Card object
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if ((permanent instanceof Permanent)
                && targetOpponent != null) {
            return ((Permanent) permanent).changeControllerId(targetOpponent.getId(), game, source);
        } else {
            discard();
        }
        return false;
    }

}
