package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkitteringPrecursor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public SkitteringPrecursor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever you sacrifice a nontoken permanent, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new SacrificePermanentTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken()), filter));
    }

    private SkitteringPrecursor(final SkitteringPrecursor card) {
        super(card);
    }

    @Override
    public SkitteringPrecursor copy() {
        return new SkitteringPrecursor(this);
    }
}
