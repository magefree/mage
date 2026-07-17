package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NorthernAirTemple extends CardImpl {

    public NorthernAirTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When Northern Air Temple enters, each opponent loses X life and you gain X life, where X is the number of Shrines you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(ShrinesYouControlCount.WHERE_X)
                .setText("each opponent loses X life"));
        ability.addEffect(new GainLifeEffect(ShrinesYouControlCount.WHERE_X).setText("and you gain X life, where X is the number of Shrines you control"));
        this.addAbility(ability.addHint(ShrinesYouControlCount.getHint()));

        // Whenever another Shrine you control enters, each opponent loses 1 life and you gain 1 life.
        ability = new EntersBattlefieldAllTriggeredAbility(new LoseLifeOpponentsEffect(1), StaticFilters.FILTER_ANOTHER_CONTROLLED_SHRINE);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private NorthernAirTemple(final NorthernAirTemple card) {
        super(card);
    }

    @Override
    public NorthernAirTemple copy() {
        return new NorthernAirTemple(this);
    }
}
