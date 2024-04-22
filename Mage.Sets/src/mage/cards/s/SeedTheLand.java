package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author LevelX2
 */
public final class SeedTheLand extends CardImpl {

    public SeedTheLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // Whenever a land enters the battlefield, its controller creates a 1/1 green Snake creature token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new CreateTokenTargetEffect(new SnakeToken()).setText("its controller creates a 1/1 green Snake creature token"),
                StaticFilters.FILTER_LAND, false, SetTargetPointer.PLAYER
        ));
    }

    private SeedTheLand(final SeedTheLand card) {
        super(card);
    }

    @Override
    public SeedTheLand copy() {
        return new SeedTheLand(this);
    }
}
