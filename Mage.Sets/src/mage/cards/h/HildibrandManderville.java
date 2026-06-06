package mage.cards.h;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.asthought.MayCastFromGraveyardAsAdventureEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HildibrandManderville extends AdventureCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HildibrandManderville(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.DETECTIVE}, "{1}{W}",
                "Gentleman's Rise",
                new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Hildibrand Manderville
        this.getLeftHalfCard().setPT(2, 2);

        // Creature tokens you control get +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // When Hildibrand Manderville dies, you may cast it from your graveyard as an Adventure until the end of your next turn.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new MayCastFromGraveyardAsAdventureEffect()));

        // Gentleman's Rise
        // Create a 2/2 black Zombie creature token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));

        finalizeCard();
    }

    private HildibrandManderville(final HildibrandManderville card) {
        super(card);
    }

    @Override
    public HildibrandManderville copy() {
        return new HildibrandManderville(this);
    }
}
