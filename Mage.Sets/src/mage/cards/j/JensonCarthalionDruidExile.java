package mage.cards.j;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JensonCarthalionDruidExile extends CardImpl {

    public JensonCarthalionDruidExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a multicolored spell, scry 1. If that spell was all colors, create a 4/4 white Angel creature token with flying and vigilance.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ScryEffect(1, false),
                StaticFilters.FILTER_SPELL_A_MULTICOLORED, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new AngelVigilanceToken()),
                JensonCarthalionDruidExileCondition.instance
        ));
        this.addAbility(ability);

        // {5}, {T}: Add {W}{U}{B}{R}{G}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 1, 1, 1, 0, 0, 0), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JensonCarthalionDruidExile(final JensonCarthalionDruidExile card) {
        super(card);
    }

    @Override
    public JensonCarthalionDruidExile copy() {
        return new JensonCarthalionDruidExile(this);
    }
}

enum JensonCarthalionDruidExileCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.castStream(
                source.getEffects()
                        .stream()
                        .map(effect -> effect.getValue("spellCast")),
                Spell.class
        ).findAny()
                .filter(Objects::nonNull)
                .map(spell -> spell.getColor(game).getColorCount())
                .orElse(0) >= 5;
    }

    @Override
    public String toString() {
        return "that spell was all colors";
    }
}
