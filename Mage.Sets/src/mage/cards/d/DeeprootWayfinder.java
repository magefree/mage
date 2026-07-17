package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeeprootWayfinder extends CardImpl {

    public DeeprootWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Deeproot Wayfinder deals combat damage to a player or battle, surveil 1, then you may return a land card from your graveyard to the battlefield tapped.
        Ability ability = new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(
                new SurveilEffect(1, false), false);
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(
                true, StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD, PutCards.BATTLEFIELD_TAPPED
        ).concatBy(", then"));
        this.addAbility(ability);
    }

    private DeeprootWayfinder(final DeeprootWayfinder card) {
        super(card);
    }

    @Override
    public DeeprootWayfinder copy() {
        return new DeeprootWayfinder(this);
    }
}
