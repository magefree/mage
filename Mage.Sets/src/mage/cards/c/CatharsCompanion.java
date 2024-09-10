package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class CatharsCompanion extends CardImpl {

    public CatharsCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever you cast a noncreature spell, Cathar's Companion gains indestructible until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));
    }

    private CatharsCompanion(final CatharsCompanion card) {
        super(card);
    }

    @Override
    public CatharsCompanion copy() {
        return new CatharsCompanion(this);
    }
}
