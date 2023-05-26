package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MizzixOfTheIzmagnus extends CardImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value greater than the number of experience counters you have");

    static {
        filter.add(new MizzixOfTheIzmagnusPredicate());
    }

    public MizzixOfTheIzmagnus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        ), filter, false));

        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MizzixOfTheIzmagnusCostReductionEffect()));
    }

    private MizzixOfTheIzmagnus(final MizzixOfTheIzmagnus card) {
        super(card);
    }

    @Override
    public MizzixOfTheIzmagnus copy() {
        return new MizzixOfTheIzmagnus(this);
    }
}

class MizzixOfTheIzmagnusPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        Spell spell = game.getStack().getSpell(input.getId());
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null) {
                if (spell.getManaValue() > controller.getCounters().getCount(CounterType.EXPERIENCE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "VariableManaCost";
    }
}

class MizzixOfTheIzmagnusCostReductionEffect extends CostModificationEffectImpl {

    MizzixOfTheIzmagnusCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have";
    }

    MizzixOfTheIzmagnusCostReductionEffect(MizzixOfTheIzmagnusCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SpellAbility spellAbility = (SpellAbility) abilityToModify;
            CardUtil.adjustCost(spellAbility, controller.getCounters().getCount(CounterType.EXPERIENCE));
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility && abilityToModify.isControlledBy(source.getControllerId())) {
            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                return StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(spell, source.getControllerId(), source, game);
            } else if (game.inCheckPlayableState()) {
                // Spell is not on the stack yet, but possible playable spells are determined
                Card sourceCard = game.getCard(abilityToModify.getSourceId());
                return sourceCard != null && StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(sourceCard, source.getControllerId(), source, game);
            }
        }
        return false;
    }

    @Override
    public MizzixOfTheIzmagnusCostReductionEffect copy() {
        return new MizzixOfTheIzmagnusCostReductionEffect(this);
    }
}
