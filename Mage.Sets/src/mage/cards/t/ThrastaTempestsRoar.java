package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TrampleOverPlaneswalkersAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author Fubs
 */
public final class ThrastaTempestsRoar extends CardImpl {

    public ThrastaTempestsRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        //This spell costs 3 less to cast for each other spell cast this turn
        ThrastaDynamicValue spellCastCount = new ThrastaDynamicValue();
        //spellCastCount does not need -1 because cast count increases only after current spell cast/reduction.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(3, spellCastCount))
                .addHint(new ValueHint("spell cast", spellCastCount))
        );

        //Trample, Haste, and Trample over planeswalkers
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(TrampleOverPlaneswalkersAbility.getInstance());

        //Thrasta has hexproof as long as it entered the battlefield this turn.
        this.addAbility(new AsEntersBattlefieldAbility(new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn)));
    }

    private ThrastaTempestsRoar(final mage.cards.t.ThrastaTempestsRoar card) {
        super(card);
    }

    @Override
    public ThrastaTempestsRoar copy() {
        return new mage.cards.t.ThrastaTempestsRoar(this);
    }
}

class ThrastaDynamicValue implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn();
        }
        return 0;
    }

    @Override
    public ThrastaDynamicValue copy() {
        return new ThrastaDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "spells cast this turn";
    }

}
