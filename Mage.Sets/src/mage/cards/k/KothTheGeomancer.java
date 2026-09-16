package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class KothTheGeomancer extends CardImpl {

    public KothTheGeomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Landfall -- Whenever a land you control enters, Koth deals 1 damage to each opponent. If that land is a Mountain, add {R}.
        this.addAbility(new LandfallAbility(new KothTheGeomancerEffect()));
    }

    private KothTheGeomancer(final KothTheGeomancer card) {
        super(card);
    }

    @Override
    public KothTheGeomancer copy() {
        return new KothTheGeomancer(this);
    }
}

class KothTheGeomancerEffect extends OneShotEffect {

    public KothTheGeomancerEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each opponent. If that land is a Mountain, add {R}.";
    }

    private KothTheGeomancerEffect(final KothTheGeomancerEffect effect) {
        super(effect);
    }

    @Override
    public KothTheGeomancerEffect copy() {
        return new KothTheGeomancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.damage(1, source.getSourceId(), source, game);
            }
        }
        Permanent triggeringLand = ((LandfallAbility) source).getTriggeringPermanent();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && triggeringLand != null && triggeringLand.hasSubtype(SubType.MOUNTAIN, game)) {
            controller.getManaPool().addMana(Mana.RedMana(1), game, source);
        }
        return true;
    }
}
