package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhisperingWizard extends CardImpl {

    public WhisperingWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, create a 1/1 white Spirit creature token with flying. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SpiritWhiteToken()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ).setTriggersOnceEachTurn(true));
    }

    private WhisperingWizard(final WhisperingWizard card) {
        super(card);
    }

    @Override
    public WhisperingWizard copy() {
        return new WhisperingWizard(this);
    }
}
