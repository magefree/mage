package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.PirateToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VraskaRelicSeeker extends CardImpl {

    public VraskaRelicSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);

        this.setStartingLoyalty(6);

        //+2: Create a 2/2 black Pirate creature token with menace.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new PirateToken()), 2));

        //-3: Destroy target artifact, creature, or enchantment. Create a colorless Treasure artifact token with "T, Sacrfice this artifact. Add one mana of any color."
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addEffect(new CreateTokenEffect(new TreasureToken()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT));
        this.addAbility(ability);

        //-10: Target player's life total becomes 1
        ability = new LoyaltyAbility(new VraskaRelicSeekerLifeTotalEffect(), -10);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VraskaRelicSeeker(final VraskaRelicSeeker card) {
        super(card);
    }

    @Override
    public VraskaRelicSeeker copy() {
        return new VraskaRelicSeeker(this);
    }
}

class VraskaRelicSeekerLifeTotalEffect extends OneShotEffect {

    public VraskaRelicSeekerLifeTotalEffect() {
        super(Outcome.Benefit);
        staticText = "Target player's life total becomes 1";
    }

    public VraskaRelicSeekerLifeTotalEffect(VraskaRelicSeekerLifeTotalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.setLife(1, game, source);
            return true;
        }
        return false;
    }

    @Override
    public VraskaRelicSeekerLifeTotalEffect copy() {
        return new VraskaRelicSeekerLifeTotalEffect(this);
    }
}
