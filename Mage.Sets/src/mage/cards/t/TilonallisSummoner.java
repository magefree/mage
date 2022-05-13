package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.RedElementalToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TilonallisSummoner extends CardImpl {

    public TilonallisSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ascend
        this.addAbility(new AscendAbility());

        // Whenever Tilonalli's Summoner attacks, you may pay {X}{R}. If you do, create X 1/1 red Elemental creature tokens that are tapped and attacking. At the beginning of the next end step, exile those tokens unless you have the city's blessing.
        this.addAbility(new AttacksTriggeredAbility(new TilonallisSummonerEffect(), false).addHint(CitysBlessingHint.instance));
    }

    private TilonallisSummoner(final TilonallisSummoner card) {
        super(card);
    }

    @Override
    public TilonallisSummoner copy() {
        return new TilonallisSummoner(this);
    }
}

class TilonallisSummonerEffect extends OneShotEffect {

    public TilonallisSummonerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may pay {X}{R}. If you do, create X 1/1 red Elemental creature tokens that are tapped and attacking. At the beginning of the next end step, exile those tokens unless you have the city's blessing";
    }

    public TilonallisSummonerEffect(final TilonallisSummonerEffect effect) {
        super(effect);
    }

    @Override
    public TilonallisSummonerEffect copy() {
        return new TilonallisSummonerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts cost = new ManaCostsImpl("{X}{R}");
            if (controller.chooseUse(outcome, "Pay " + cost.getText() + "? If you do, you create X 1/1 red Elemental creature tokens that are tapped and attacking.", source, game)) {
                int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    controller.resetStoredBookmark(game); // otherwise you can undo the payment
                    CreateTokenEffect effect = new CreateTokenEffect(new RedElementalToken(), costX, true, true);
                    effect.apply(game, source);
                    Effect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD)
                            .setText("exile those tokens unless you have the city's blessing");
                    exileEffect.setTargetPointer(new FixedTargets(new CardsImpl(effect.getLastAddedTokenIds()), game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                            exileEffect, TargetController.ANY, new InvertCondition(CitysBlessingCondition.instance)), source);
                }
            }
            return true;
        }
        return false;
    }
}
