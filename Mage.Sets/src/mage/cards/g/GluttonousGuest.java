package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GluttonousGuest extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Blood token");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(SubType.BLOOD.getPredicate());
    }

    public GluttonousGuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Gluttonous Guest enters the battlefield, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));

        // Whenever you sacrifice a Blood token, you gain 1 life.
        this.addAbility(new SacrificePermanentTriggeredAbility(new GainLifeEffect(1), filter));
    }

    private GluttonousGuest(final GluttonousGuest card) {
        super(card);
    }

    @Override
    public GluttonousGuest copy() {
        return new GluttonousGuest(this);
    }
}
