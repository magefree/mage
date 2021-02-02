package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ShrewdHatchling extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("blue spell");
    private static final FilterSpell filter2 = new FilterSpell("red spell");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter2.add(new ColorPredicate(ObjectColor.RED));
    }

    private String rule = "Whenever you cast a blue spell, remove a -1/-1 counter from Shrewd Hatchling.";
    private String rule2 = "Whenever you cast a red spell, remove a -1/-1 counter from Shrewd Hatchling.";

    public ShrewdHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U/R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Shrewd Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4))));

        // {UR}: Target creature can't block Shrewd Hatchling this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShrewdHatchlingEffect(), new ManaCostsImpl("{U/R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you cast a blue spell, remove a -1/-1 counter from Shrewd Hatchling.
        this.addAbility(new SpellCastAllTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filter, false, rule));

        // Whenever you cast a red spell, remove a -1/-1 counter from Shrewd Hatchling.
        this.addAbility(new SpellCastAllTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filter2, false, rule2));

    }

    private ShrewdHatchling(final ShrewdHatchling card) {
        super(card);
    }

    @Override
    public ShrewdHatchling copy() {
        return new ShrewdHatchling(this);
    }
}

class ShrewdHatchlingEffect extends RestrictionEffect {

    public ShrewdHatchlingEffect() {
        super(Duration.EndOfTurn);
        staticText = "Target creature can't block {this} this turn";
    }

    public ShrewdHatchlingEffect(final ShrewdHatchlingEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        UUID targetId = source.getFirstTarget();
        return targetId == null || !blocker.getId().equals(targetId);
    }

    @Override
    public ShrewdHatchlingEffect copy() {
        return new ShrewdHatchlingEffect(this);
    }

}
