package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class DarkDwellerOracle extends CardImpl {

    public DarkDwellerOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice a creature: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new DarkDwellerOracleExileEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    public DarkDwellerOracle(final DarkDwellerOracle card) {
        super(card);
    }

    @Override
    public DarkDwellerOracle copy() {
        return new DarkDwellerOracle(this);
    }
}

class DarkDwellerOracleExileEffect extends OneShotEffect {

    public DarkDwellerOracleExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. "
                + "You may play that card this turn. "
                + "<i>(You still pay its costs. "
                + "You can play a land this way only if "
                + "you have an available land play remaining.)</i>";
    }

    public DarkDwellerOracleExileEffect(final DarkDwellerOracleExileEffect effect) {
        super(effect);
    }

    @Override
    public DarkDwellerOracleExileEffect copy() {
        return new DarkDwellerOracleExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().hasCards()) {
            Library library = controller.getLibrary();
            Card card = library.getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                ContinuousEffect effect = new DarkDwellerOracleCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class DarkDwellerOracleCastFromExileEffect extends AsThoughEffectImpl {

    public DarkDwellerOracleCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public DarkDwellerOracleCastFromExileEffect(final DarkDwellerOracleCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DarkDwellerOracleCastFromExileEffect copy() {
        return new DarkDwellerOracleCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
