package mage.cards.u;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetOpponent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Urabrask extends CardImpl {

    public Urabrask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.t.TheGreatWork.class;

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Urabrask deals 1 damage to target opponent. Add {R}.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(1), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new BasicManaEffect(Mana.RedMana(1)));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {R}: Exile Urabrask, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery and only if you've cast three or more instant and/or sorcery spells this turn.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{R}"), UrabraskCondition.instance
        ).setTiming(TimingRule.SORCERY), new SpellsCastWatcher());
    }

    private Urabrask(final Urabrask card) {
        super(card);
    }

    @Override
    public Urabrask copy() {
        return new Urabrask(this);
    }
}

enum UrabraskCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .filter(spell -> spell.isInstantOrSorcery(game))
                .count() >= 3;
    }

    @Override
    public String toString() {
        return "if you've cast three or more instant and/or sorcery spells this turn";
    }
}