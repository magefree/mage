package mage.cards.b;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class BorgIncursion extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Artifact creatures");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public BorgIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new BorgToken(), 2
        ));
        this.getSpellAbility().addEffect(new GainProtectionFromColorAllEffect(Duration.EndOfTurn, filter));
    }

    private BorgIncursion(final BorgIncursion card) {
        super(card);
    }

    @Override
    public BorgIncursion copy() {
        return new BorgIncursion(this);
    }
}