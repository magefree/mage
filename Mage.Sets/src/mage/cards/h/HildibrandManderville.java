package mage.cards.h;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{W}", "Gentleman's Rise", "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creature tokens you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // When Hildibrand Manderville dies, you may cast it from your graveyard as an Adventure until the end of your next turn.
        this.addAbility(new DiesSourceTriggeredAbility(new MayCastFromGraveyardAsAdventureEffect()));

        // Gentleman's Rise
        // Create a 2/2 black Zombie creature token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));

        this.finalizeAdventure();
    }

    private HildibrandManderville(final HildibrandManderville card) {
        super(card);
    }

    @Override
    public HildibrandManderville copy() {
        return new HildibrandManderville(this);
    }
}
