
package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MagusOfTheMindEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability, new CastSpellLastTurnWatcher());
    }

    public MagusOfTheMind(final MagusOfTheMind card) {
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
        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
        int stormCount = watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn() + 1;
        System.out.println(stormCount);
        if (controller != null && sourceObject != null) {
            controller.shuffleLibrary(source, game);
            if (controller.getLibrary().hasCards()) {
                Set<Card> cards = controller.getLibrary().getTopCards(game, stormCount);
                if (cards != null) {
                    for (Card card : cards) {
                        if (card != null) {
                            controller.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                            ContinuousEffect effect = new MagusOfTheMindCastFromExileEffect();
                            effect.setTargetPointer(new FixedTarget(card.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class MagusOfTheMindCastFromExileEffect extends AsThoughEffectImpl {

    MagusOfTheMindCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may play that card without paying its mana cost";
    }

    MagusOfTheMindCastFromExileEffect(final MagusOfTheMindCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MagusOfTheMindCastFromExileEffect copy() {
        return new MagusOfTheMindCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId != null && objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                Card card = game.getCard(objectId);
                if (card != null && game.getState().getZone(objectId) == Zone.EXILED) {
                    if (!card.isLand() && card.getSpellAbility().getCosts() != null) {
                        Player player = game.getPlayer(affectedControllerId);
                        if (player != null) {
                            player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
