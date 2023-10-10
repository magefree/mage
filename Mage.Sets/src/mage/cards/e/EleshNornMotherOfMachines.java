package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.AdditionalTriggerControlledETBReplacementEffect;
import mage.abilities.effects.common.ruleModifying.DontCauseTriggerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class EleshNornMotherOfMachines extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanents your opponents control");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public EleshNornMotherOfMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN, SubType.PRAETOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If a permanent entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerControlledETBReplacementEffect()));

        // Permanents entering the battlefield don't cause abilities of permanents your opponents control to trigger.
        this.addAbility(new SimpleStaticAbility(new DontCauseTriggerEffect(StaticFilters.FILTER_PERMANENTS, false, filter)));
    }

    private EleshNornMotherOfMachines(final EleshNornMotherOfMachines card) {super(card);}

    @Override
    public EleshNornMotherOfMachines copy() {return new EleshNornMotherOfMachines(this);}
}
