package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.WebSlingingCondition;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ScarletSpiderBenReilly extends CardImpl {

    public ScarletSpiderBenReilly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Web-slinging {R}{G}
        this.addAbility(new WebSlingingAbility(this, "{R}{G}"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Sensational Save -- If Scarlet Spider was cast using web-slinging, he enters with X +1/+1 counters on him, where X is the mana value of the returned creature.
        String ruleText = CardUtil.italicizeWithEmDash("Sensational Save") + "If {this} was cast using web-slinging, " +
                "he enters with X +1/+1 counters on him, where X is the mana value of the returned creature.";
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(),
                ScarletSpiderBenReillyValue.instance, false),
                WebSlingingCondition.THIS, ruleText, ""));
    }

    private ScarletSpiderBenReilly(final ScarletSpiderBenReilly card) {
        super(card);
    }

    @Override
    public ScarletSpiderBenReilly copy() {
        return new ScarletSpiderBenReilly(this);
    }
}
enum ScarletSpiderBenReillyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {

        Spell spell = game.getSpellOrLKIStack(sourceAbility.getSourceId());
        if (spell == null || spell.getSpellAbility() == null) {
            return 0;
        }

        ReturnToHandChosenControlledPermanentCost returnCost = (ReturnToHandChosenControlledPermanentCost) spell.getSpellAbility().getCosts().stream()
                .filter(cost -> cost instanceof ReturnToHandChosenControlledPermanentCost)
                .findFirst()
                .orElse(null);
        if (returnCost == null || returnCost.getPermanents().isEmpty()) {
            return 0;
        }

        return returnCost.getPermanents().get(0).getManaValue();
    }

    @Override
    public ScarletSpiderBenReillyValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
