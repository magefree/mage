package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class NevThePracticalDean extends CardImpl {

    private static final FilterCreaturePermanent filter =
        new FilterCreaturePermanent("creatures you control with counters on them");
    private static final FilterSpell filter2 = new FilterSpell("spell with {X} in its mana cost");

    static {
        filter.add(CounterAnyPredicate.instance);
        filter2.add(VariableManaCostPredicate.instance);
    }

    public NevThePracticalDean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control with counters on them have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("creatures you control with counters on them have trample")));

        // Whenever you cast your first spell with {X} in its mana cost each turn, put X +1/+1 counters on Nev.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new NevAddXCountersEffect(), filter2, false
        ).setTriggersLimitEachTurn(1));
    }

    private NevThePracticalDean(final NevThePracticalDean card) {
        super(card);
    }

    @Override
    public NevThePracticalDean copy() {
        return new NevThePracticalDean(this);
    }
}

class NevAddXCountersEffect extends OneShotEffect {

    NevAddXCountersEffect() {
        super(Outcome.Benefit);
        staticText = "put X +1/+1 counters on {this}";
    }

    private NevAddXCountersEffect(final NevAddXCountersEffect effect) {
        super(effect);
    }

    @Override
    public NevAddXCountersEffect copy() {
        return new NevAddXCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        int xValue = CardUtil.getSourceCostsTag(game, spell.getSpellAbility(), "X", 0);
        if (xValue <= 0) {
            return false;
        }
        Permanent nev = source.getSourcePermanentIfItStillExists(game);
        if (nev == null) {
            return false;
        }
        nev.addCounters(CounterType.P1P1.createInstance(xValue), source.getControllerId(), source, game);
        return true;
    }
}
