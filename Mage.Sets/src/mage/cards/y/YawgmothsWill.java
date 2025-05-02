package mage.cards.y;

import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class YawgmothsWill extends CardImpl {

    public YawgmothsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Until end of turn, you may play lands and cast spells from your graveyard.
        this.getSpellAbility().addEffect(PlayFromGraveyardControllerEffect.playLandsAndCastSpells(Duration.EndOfTurn));

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.getSpellAbility().addEffect(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn).concatBy("<br>"));
    }

    private YawgmothsWill(final YawgmothsWill card) {
        super(card);
    }

    @Override
    public YawgmothsWill copy() {
        return new YawgmothsWill(this);
    }
}
