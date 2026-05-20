package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MicaReaderOfRuins extends CardImpl {

    public MicaReaderOfRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward--Pay 3 life.
        this.addAbility(new WardAbility(new PayLifeCost(3)));

        // Whenever you cast an instant or sorcery spell, you may sacrifice an artifact. If you do, copy that spell and you may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new CopyStackObjectEffect("that spell"),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT)
        ), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false, SetTargetPointer.SPELL));
    }

    private MicaReaderOfRuins(final MicaReaderOfRuins card) {
        super(card);
    }

    @Override
    public MicaReaderOfRuins copy() {
        return new MicaReaderOfRuins(this);
    }
}
