package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author weirddan455
 */
public final class Demilich extends CardImpl {

    public Demilich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}{U}{U}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // This spell costs {U} less to cast for each instant and sorcery you've cast this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(
                new ManaCostsImpl<>("{U}"), DemilichValue.instance
        )).addHint(new ValueHint("Instants and sorceries you've cast this turn", DemilichValue.instance)), new SpellsCastWatcher());

        // Whenever Demilich attacks, exile up to one target instant or sorcery card from your graveyard. Copy it. You may cast the copy.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetCardCopyAndCastEffect(false).setText(
                "exile up to one target instant or sorcery card from your graveyard. Copy it. You may cast the copy"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // You may cast Demilich from your graveyard by exiling four instants and/or sorcery cards from your graveyard in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new DemilichPlayEffect()));
    }

    private Demilich(final Demilich card) {
        super(card);
    }

    @Override
    public Demilich copy() {
        return new Demilich(this);
    }
}

enum DemilichValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int spells = 0;
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            for (Spell spell : watcher.getSpellsCastThisTurn(sourceAbility.getControllerId())) {
                if (spell.isInstantOrSorcery(game)) {
                    spells++;
                }
            }
        }
        return spells;
    }

    @Override
    public DemilichValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "instant and sorcery spell you've cast this turn";
    }
}

class DemilichPlayEffect extends AsThoughEffectImpl {

    public DemilichPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "You may cast {this} from your graveyard by exiling four instant and/or sorcery cards from your graveyard in addition to paying its other costs";
    }

    private DemilichPlayEffect(final DemilichPlayEffect effect) {
        super(effect);
    }

    @Override
    public DemilichPlayEffect copy() {
        return new DemilichPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getSourceId().equals(objectId) && source.isControlledBy(affectedControllerId)
                && game.getState().getZone(objectId) == Zone.GRAVEYARD) {
            Player controller = game.getPlayer(affectedControllerId);
            if (controller != null) {
                Costs<Cost> costs = new CostsImpl<>();
                costs.add(new ExileFromGraveCost(new TargetCardInYourGraveyard(4, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD)));
                controller.setCastSourceIdWithAlternateMana(objectId, new ManaCostsImpl<>("{U}{U}{U}{U}"), costs);
                return true;
            }
        }
        return false;
    }
}
