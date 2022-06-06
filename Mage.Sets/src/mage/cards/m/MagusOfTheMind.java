package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagusOfTheMind extends CardImpl {

    public MagusOfTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // U, T, Sacrifice Magus of the Mind: Shuffle your library, then exile the top X cards, where X is one plus the number of spells cast this turn. Until end of turn, you may play cards exiled this way without paying their mana costs.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MagusOfTheMindEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability, new CastSpellLastTurnWatcher());
    }

    private MagusOfTheMind(final MagusOfTheMind card) {
        super(card);
    }

    @Override
    public MagusOfTheMind copy() {
        return new MagusOfTheMind(this);
    }
}

class MagusOfTheMindEffect extends OneShotEffect {

    MagusOfTheMindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle your library, then exile the top X cards, where X is one plus the number of spells cast this turn. Until end of turn, you may play cards exiled this way without paying their mana costs";
    }

    MagusOfTheMindEffect(final MagusOfTheMindEffect effect) {
        super(effect);
    }

    @Override
    public MagusOfTheMindEffect copy() {
        return new MagusOfTheMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        int stormCount = watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn() + 1;
        if (controller != null && sourceObject != null) {
            controller.shuffleLibrary(source, game);
            if (controller.getLibrary().hasCards()) {
                Set<Card> cards = controller.getLibrary().getTopCards(game, stormCount);
                return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, cards,
                        TargetController.YOU, Duration.EndOfTurn, true, false, false);
            }
            return true;
        }
        return false;
    }
}
