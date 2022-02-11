package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunoStromkirk extends CardImpl {

    public RunoStromkirk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.k.KrothussLordOfTheDeep.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Runo Stromkirk enters the battlefield, put up to one target creature card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If a creature card with mana value 6 or greater is revealed this way, transform Runo Stromkirk.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RunoStromkirkEffect(), TargetController.YOU, false
        ));
    }

    private RunoStromkirk(final RunoStromkirk card) {
        super(card);
    }

    @Override
    public RunoStromkirk copy() {
        return new RunoStromkirk(this);
    }
}

class RunoStromkirkEffect extends OneShotEffect {

    RunoStromkirkEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may reveal that card. " +
                "If a creature card with mana value 6 or greater is revealed this way, transform {this}";
    }

    private RunoStromkirkEffect(final RunoStromkirkEffect effect) {
        super(effect);
    }

    @Override
    public RunoStromkirkEffect copy() {
        return new RunoStromkirkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards(null, card, game);
        if (!player.chooseUse(outcome, "Reveal " + card.getName() + '?', source, game)) {
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (!card.isCreature(game) || card.getManaValue() < 6) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.transform(source, game);
        }
        return true;
    }
}
