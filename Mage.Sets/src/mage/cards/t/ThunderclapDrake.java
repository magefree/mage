package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CommanderCastFromCommandZoneValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThunderclapDrake extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery spells");

    public ThunderclapDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // {2}{U}, Sacrifice Thunderclap Drake: When you cast your next instant or sorcery spell this turn, copy it for each time you've cast your commander from the command zone this game. You may choose new targets for the copies.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new CopyNextSpellDelayedTriggeredAbility(
                                StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY,
                                new ThunderclapDrakeEffect(),
                                "When you cast your next instant or sorcery spell this turn, "
                                        + "copy it for each time you've cast your commander from the command zone this game. "
                                        + "You may choose new targets for the copies."
                        )
                ),
                new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addHint(CommanderCastFromCommandZoneValue.getHint());
        this.addAbility(ability);
    }

    private ThunderclapDrake(final ThunderclapDrake card) {
        super(card);
    }

    @Override
    public ThunderclapDrake copy() {
        return new ThunderclapDrake(this);
    }
}

// Inspired by CommanderStormEffect
class ThunderclapDrakeEffect extends OneShotEffect {

    ThunderclapDrakeEffect() {
        super(Outcome.Copy);
        staticText = "copy it for each time you've cast your commander from the command zone this game. "
                + "You may choose new targets for the copies";
    }

    private ThunderclapDrakeEffect(final ThunderclapDrakeEffect effect) {
        super(effect);
    }

    @Override
    public ThunderclapDrakeEffect copy() {
        return new ThunderclapDrakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) this.getValue("spellCast");
        int count = CommanderCastFromCommandZoneValue.instance.calculate(game, source, this);
        if (spell == null || player == null || count <= 0) {
            return false;
        }
        game.informPlayers(spell.getLogName() + " will be copied " + count + " time" + (count > 1 ? "s" : "") + CardUtil.getSourceLogName(game, source));
        spell.createCopyOnStack(game, source, source.getControllerId(), true, count);
        return true;
    }
}
