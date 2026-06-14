package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class QuakeAgentOfSHIELD extends CardImpl {

    public QuakeAgentOfSHIELD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INHUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Seismic Takedown -- Whenever you cast a noncreature spell, tap target creature or land.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new TapTargetEffect(),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND));
        this.addAbility(ability.withFlavorWord("Seismic Takedown"));
    }

    private QuakeAgentOfSHIELD(final QuakeAgentOfSHIELD card) {
        super(card);
    }

    @Override
    public QuakeAgentOfSHIELD copy() {
        return new QuakeAgentOfSHIELD(this);
    }
}
