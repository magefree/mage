package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfTheRestlessDead extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(TargetController.ENCHANTED.getControllerPredicate());
    }

    public CurseOfTheRestlessDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever a land enters the battlefield under enchanted player's control, you create a 2/2 black Zombie creature token with decayed.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new ZombieDecayedToken()),
                filter, "Whenever a land enters the battlefield under enchanted player's control, " +
                "you create a 2/2 black Zombie creature token with decayed."
        ));
    }

    private CurseOfTheRestlessDead(final CurseOfTheRestlessDead card) {
        super(card);
    }

    @Override
    public CurseOfTheRestlessDead copy() {
        return new CurseOfTheRestlessDead(this);
    }
}
