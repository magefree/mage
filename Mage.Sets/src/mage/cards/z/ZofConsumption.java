package mage.cards.z;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ZofConsumption extends ModalDoubleFacedCard {

    public ZofConsumption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{4}{B}{B}",
                "Zof Bloodbog", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Zof Consumption
        // Sorcery

        // Each opponent loses 4 life and you gain 4 life.
        this.getLeftHalfCard().getSpellAbility().addEffect(new LoseLifeOpponentsEffect(4));
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainLifeEffect(4).concatBy("and"));

        // 2.
        // Zof Bloodbog
        // Land

        // Zof Bloodbog enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private ZofConsumption(final ZofConsumption card) {
        super(card);
    }

    @Override
    public ZofConsumption copy() {
        return new ZofConsumption(this);
    }
}
