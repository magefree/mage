package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.DynamicCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarshlandBloodcaster extends CardImpl {

    public MarshlandBloodcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{B}, {T}: Rather than pay the mana cost of the next spell you cast this turn, you may pay life equal to that spell's mana value.
        Ability ability = new SimpleActivatedAbility(new MarshlandBloodcasterEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new SpellsCastWatcher());
    }

    private MarshlandBloodcaster(final MarshlandBloodcaster card) {
        super(card);
    }

    @Override
    public MarshlandBloodcaster copy() {
        return new MarshlandBloodcaster(this);
    }
}

class MarshlandBloodcasterEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("a spell");
    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(
            SourceIsSpellCondition.instance, null, filter, true, MarshlandBloodcasterCost.instance
    );
    private int spellsCast = -1;

    public MarshlandBloodcasterEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "rather than pay the mana cost of the next spell you cast this turn, " +
                "you may pay life equal to that spell's mana value";
    }

    public MarshlandBloodcasterEffect(final MarshlandBloodcasterEffect effect) {
        super(effect);
    }

    @Override
    public MarshlandBloodcasterEffect copy() {
        return new MarshlandBloodcasterEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
        spellsCast = getSpellsCast(source.getControllerId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getSpellsCast(source.getControllerId(), game) > spellsCast) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }

    private static int getSpellsCast(UUID playerId, Game game) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null ? watcher.getSpellsCastThisTurn(playerId).size() : 0;
    }
}

enum MarshlandBloodcasterCost implements DynamicCost {
    instance;

    @Override
    public Cost getCost(Ability ability, Game game) {
        return new PayLifeCost(ability.getManaCosts().manaValue());
    }

    @Override
    public String getText(Ability ability, Game game) {
        return "Pay " + ability.getManaCosts().manaValue() + " life rather than "
                + ability.getManaCosts().getText() + '?';
    }
}
