package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CheerfulOsteomancer extends PrepareCard {

    public CheerfulOsteomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}", "Raise Dead", CardType.SORCERY, "{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Raise Dead
        // Sorcery {B}
        // Return target creature card from your graveyard to your hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
    }

    private CheerfulOsteomancer(final CheerfulOsteomancer card) {
        super(card);
    }

    @Override
    public CheerfulOsteomancer copy() {
        return new CheerfulOsteomancer(this);
    }
}
