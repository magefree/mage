package mage.cards.d;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaybreakChimera extends CardImpl {

    public DaybreakChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {X} less to cast, where X is your devotion to white.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new DaybreakChimeraEffect()).addHint(DevotionCount.W.getHint()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private DaybreakChimera(final DaybreakChimera card) {
        super(card);
    }

    @Override
    public DaybreakChimera copy() {
        return new DaybreakChimera(this);
    }
}

class DaybreakChimeraEffect extends CostModificationEffectImpl {

    DaybreakChimeraEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is your devotion to white. " +
                "<i>(Each {W} in the mana costs of permanents you control counts toward your devotion to white.)</i>";
    }

    private DaybreakChimeraEffect(final DaybreakChimeraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() == 0) {
            return false;
        }
        int count = DevotionCount.W.calculate(game, source, this);
        mana.setGeneric(Math.max(mana.getGeneric() - count, 0));
        spellAbility.getManaCostsToPay().load(mana.toString());
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public DaybreakChimeraEffect copy() {
        return new DaybreakChimeraEffect(this);
    }
}
