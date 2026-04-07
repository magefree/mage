package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class CelesRuneKnight extends CardImpl {

    public CelesRuneKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Celes enters, discard any number of cards, then draw that many cards plus one.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardAndDrawThatManyEffect(Integer.MAX_VALUE, 1)));

        // Whenever one or more other creatures you control enter, if one or more of them entered from a graveyard or was cast from a graveyard, put a +1/+1 counter on each creature you control.
        this.addAbility(new CelesRuneKnightTriggeredAbility());
    }

    private CelesRuneKnight(final CelesRuneKnight card) {
        super(card);
    }

    @Override
    public CelesRuneKnight copy() {
        return new CelesRuneKnight(this);
    }
}

class CelesRuneKnightTriggeredAbility extends EntersBattlefieldOneOrMoreTriggeredAbility {

    CelesRuneKnightTriggeredAbility() {
        super(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES, TargetController.YOU);
        setTriggerPhrase("Whenever one or more other creatures you control enter, "
            + "if one or more of them entered from a graveyard or was cast from a graveyard, ");
    }

    private CelesRuneKnightTriggeredAbility(final CelesRuneKnightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CelesRuneKnightTriggeredAbility copy() {
        return new CelesRuneKnightTriggeredAbility(this);
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (super.checkEvent(event, game)) {
            Zone fromZone = event.getFromZone();
            if (fromZone == Zone.GRAVEYARD) {
                return true;
            }
            if (fromZone == Zone.STACK) {
                Permanent permanent = event.getTarget();
                Spell spell = game.getSpellOrLKIStack(permanent.getId());
                if (spell != null && spell.getFromZone() == Zone.GRAVEYARD) {
                    return true;
                }
            }
        }
        return false;
    }
}
