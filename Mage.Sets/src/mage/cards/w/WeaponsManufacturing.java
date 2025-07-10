package mage.cards.w;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.MunitionsToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeaponsManufacturing extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("nontoken artifact you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public WeaponsManufacturing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever a nontoken artifact you control enters, create a colorless artifact token named Munitions with "When this token leaves the battlefield, it deals 2 damage to any target."
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new CreateTokenEffect(new MunitionsToken()), filter));
    }

    private WeaponsManufacturing(final WeaponsManufacturing card) {
        super(card);
    }

    @Override
    public WeaponsManufacturing copy() {
        return new WeaponsManufacturing(this);
    }
}
