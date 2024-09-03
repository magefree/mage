package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class ForsakenCrossroads extends CardImpl {

    public ForsakenCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // Forsaken Crossroads enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As Forsaken Crossroads enters, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // When Forskaken Crossroads enters, scry 1. If you weren’t the starting player, you may untap Forsaken Crossroads instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ForsakenCrossroadsEffect()));

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ForsakenCrossroads(final mage.cards.f.ForsakenCrossroads card) {
        super(card);
    }

    @Override
    public mage.cards.f.ForsakenCrossroads copy() {
        return new mage.cards.f.ForsakenCrossroads(this);
    }
}

class ForsakenCrossroadsEffect extends OneShotEffect {

    ForsakenCrossroadsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "When {this} enters, scry 1. If you weren't the starting player, you may untap {this} instead.";
    }

    private ForsakenCrossroadsEffect(final ForsakenCrossroadsEffect effect) {
        super(effect);
    }

    @Override
    public ForsakenCrossroadsEffect copy() {
        return new ForsakenCrossroadsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (!controller.getId().equals(game.getStartingPlayerId())) {
            if (controller.chooseUse(Outcome.Untap, "Untap {this} instead of scrying 1?", "", "Untap", "Scry 1", source, game)) {
                Card card = source.getSourceCardIfItStillExists(game);
                Permanent permanent = game.getPermanent(card.getId());
                permanent.untap(game);
                return true;
            }
        }
        OneShotEffect scryEffect = new ScryEffect(1);
        scryEffect.apply(game, source);
        return true;
    }
}

