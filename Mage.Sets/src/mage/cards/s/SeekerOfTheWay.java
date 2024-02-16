package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class SeekerOfTheWay extends CardImpl {

    public SeekerOfTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());
        
        // Whenever you cast a noncreature spell, Seeker of the Way gains lifelink until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)
                .setText("{this} gains lifelink until end of turn"), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));
    }

    private SeekerOfTheWay(final SeekerOfTheWay card) {
        super(card);
    }

    @Override
    public SeekerOfTheWay copy() {
        return new SeekerOfTheWay(this);
    }
}
