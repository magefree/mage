package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.command.emblems.ChandraRoaringFlameEmblem;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChandraRoaringFlame extends CardImpl {

    public ChandraRoaringFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.color.setRed(true);

        this.nightCard = true;

        this.setStartingLoyalty(4);

        // +1: Chandra, Roaring Flame deals 2 damage to target player.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(2), 1);
        loyaltyAbility.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(loyaltyAbility);

        //-2: Chandra, Roaring Flame deals 2 damage to target creature.
        loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(2), -2);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(loyaltyAbility);

        //-7: Chandra, Roaring Flame deals 6 damage to each opponent.  Each player dealt damage this way gets an emblem with "At the beginning of your upkeep, this emblem deals 3 damage to you."
        this.addAbility(new LoyaltyAbility(new ChandraRoaringFlameEmblemEffect(), -7));

    }

    private ChandraRoaringFlame(final ChandraRoaringFlame card) {
        super(card);
    }

    @Override
    public ChandraRoaringFlame copy() {
        return new ChandraRoaringFlame(this);
    }
}

class ChandraRoaringFlameEmblemEffect extends OneShotEffect {

    public ChandraRoaringFlameEmblemEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 6 damage to each opponent. Each player dealt damage this way gets an emblem with \"At the beginning of your upkeep, this emblem deals 3 damage to you.\"";
    }

    public ChandraRoaringFlameEmblemEffect(final ChandraRoaringFlameEmblemEffect effect) {
        super(effect);
    }

    @Override
    public ChandraRoaringFlameEmblemEffect copy() {
        return new ChandraRoaringFlameEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Player> opponentsEmblem = new ArrayList<>();
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.damage(6, source.getSourceId(), source, game) > 0) {
                        opponentsEmblem.add(opponent);
                    }
                }
            }
            for (Player opponent : opponentsEmblem) {
                game.addEmblem(new ChandraRoaringFlameEmblem(), source.getSourceObject(game), opponent.getId());
            }
        }
        return false;
    }
}
