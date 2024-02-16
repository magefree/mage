package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoiseMarine extends CardImpl {

    public NoiseMarine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Cascade
        this.addAbility(new CascadeAbility());

        // Sonic Blaster -- When Noise Marine enters the battlefield, it deals damage equal to the number of spells you've cast this turn to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(NoiseMarineValue.instance, "it")
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Sonic Blaster"));
    }

    private NoiseMarine(final NoiseMarine card) {
        super(card);
    }

    @Override
    public NoiseMarine copy() {
        return new NoiseMarine(this);
    }
}

enum NoiseMarineValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher == null ? 0 : watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public NoiseMarineValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "spells you've cast this turn";
    }
}
