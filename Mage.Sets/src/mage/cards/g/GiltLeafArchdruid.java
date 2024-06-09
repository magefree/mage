package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlAllControlledTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class GiltLeafArchdruid extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Druid spell");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.DRUID, "untapped Druids you control");

    static {
        filter.add(SubType.DRUID.getPredicate());
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public GiltLeafArchdruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Druid spell, you may draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, true
        ));

        // Tap seven untapped Druids you control: Gain control of all lands target player controls.
        Ability ability = new SimpleActivatedAbility(
                new GainControlAllControlledTargetEffect(StaticFilters.FILTER_LANDS),
                new TapTargetCost(new TargetControlledPermanent(7, filter2))
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GiltLeafArchdruid(final GiltLeafArchdruid card) {
        super(card);
    }

    @Override
    public GiltLeafArchdruid copy() {
        return new GiltLeafArchdruid(this);
    }
}