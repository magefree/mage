package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class GaeasWill extends CardImpl {

    public GaeasWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setGreen(true);

        // Suspend 4—{G}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{G}"), this));

        // Until end of turn, you may play lands and cast spells from your graveyard.
        this.getSpellAbility().addEffect(PlayFromGraveyardControllerEffect.playLandsAndCastSpells(Duration.EndOfTurn));

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn)));
    }

    private GaeasWill(final GaeasWill card) {
        super(card);
    }

    @Override
    public GaeasWill copy() {
        return new GaeasWill(this);
    }
}
