package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheSixthDoctor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public TheSixthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Time Lord's Prerogative -- Whenever you cast a historic spell, copy it, except the copy isn't legendary. This ability triggers only once each turn.
        this.addAbility(
                new SpellCastControllerTriggeredAbility(
                        new CopyTargetSpellEffect(
                                false, true, false, 1,
                                new RemoveTypeCopyApplier(SuperType.LEGENDARY)
                        ).setText("copy it, except the copy isn't legendary"),
                        filter, false, SetTargetPointer.SPELL
                ).setTriggersOnceEachTurn(true).withFlavorWord("Time Lord's Prerogative")
        );
    }

    private TheSixthDoctor(final TheSixthDoctor card) {
        super(card);
    }

    @Override
    public TheSixthDoctor copy() {
        return new TheSixthDoctor(this);
    }
}
