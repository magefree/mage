package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.FractalToken;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeekahFractalTheorist extends CardImpl {

    public DeekahFractalTheorist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is that spell's mana value.
        this.addAbility(new MagecraftAbility(FractalToken.getEffect(
                DeekahFractalTheoristValue.instance, "Put X +1/+1 counters on it, " +
                        "where X is that spell's mana value"
        )));

        // {3}{U}: Target creature token can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_TOKEN));
        this.addAbility(ability);
    }

    private DeekahFractalTheorist(final DeekahFractalTheorist card) {
        super(card);
    }

    @Override
    public DeekahFractalTheorist copy() {
        return new DeekahFractalTheorist(this);
    }
}

enum DeekahFractalTheoristValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue(MagecraftAbility.SPELL_KEY);
        return spell != null ? spell.getManaValue() : 0;
    }

    @Override
    public DeekahFractalTheoristValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}