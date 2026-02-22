package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class RoothaMasteringTheMoment extends CardImpl {

    private static final Hint hint = new ValueHint(
        "the greatest mana among instant and sorcery spells you've cast this turn",
        RoothaMasteringTheMomentDynamicValue.instance
    );

    public RoothaMasteringTheMoment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, if you've cast an instant or sorcery spell this turn,
        // create an X/X blue and red Elemental creature token with flying and haste, where X is the
        //  greatest mana value among instant and sorcery spells you've cast this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new RoothaMasteringTheMomentEffect())
            .withInterveningIf(RoothaMasteringTheMomentCondition.instance).addHint(hint));
    }

    private RoothaMasteringTheMoment(final RoothaMasteringTheMoment card) {
        super(card);
    }

    @Override
    public RoothaMasteringTheMoment copy() {
        return new RoothaMasteringTheMoment(this);
    }
}

enum RoothaMasteringTheMomentDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return 0;
        }

        return watcher
            .getSpellsCastThisTurn(sourceAbility.getControllerId())
            .stream()
            .filter(s -> s.isInstantOrSorcery(game))
            .mapToInt(Spell::getManaValue)
            .max().orElse(0);
    }

    @Override
    public RoothaMasteringTheMomentDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the greatest mana among instant and sorcery spells you've cast this turn";
    }
}

enum RoothaMasteringTheMomentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        List<Spell> spells = game.getState().getWatcher(SpellsCastWatcher.class)
            .getSpellsCastThisTurn(source.getControllerId());
        for (Spell spell : spells) {
            if (spell.isInstantOrSorcery(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you've cast an instant or sorcery spell this turn";
    }
}

class RoothaMasteringTheMomentEffect extends OneShotEffect {

    RoothaMasteringTheMomentEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X blue and red Elemental creature token with flying and haste, "
         + "where X is the greatest mana value among instant and sorcery spells you've cast this turn";
    }

    private RoothaMasteringTheMomentEffect(final RoothaMasteringTheMomentEffect effect) {
        super(effect);
    }

    @Override
    public RoothaMasteringTheMomentEffect copy() {
        return new RoothaMasteringTheMomentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = RoothaMasteringTheMomentDynamicValue.instance.calculate(game, source, this);
        // TODO: After the set reveals, confirm there isn't a valid printed Token to use instead. Otherwise delete this comment.
        return new CreatureToken(
            xValue, xValue,
            "X/X blue and red Elemental creature token with flying and haste",
                SubType.ELEMENTAL
        ).withColor("UR").withAbility(FlyingAbility.getInstance()).withAbility(HasteAbility.getInstance())
        .putOntoBattlefield(1, game, source);
    }
}
