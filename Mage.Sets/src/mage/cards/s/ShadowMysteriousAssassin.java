package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowMysteriousAssassin extends CardImpl {

    public ShadowMysteriousAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Throw -- Whenever Shadow deals combat damage to a player, you may sacrifice another nonland permanent. If you do, draw two cards and each opponent loses life equal to the mana value of the sacrificed permanent.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ShadowMysteriousAssassinEffect()).withFlavorWord("Throw"));
    }

    private ShadowMysteriousAssassin(final ShadowMysteriousAssassin card) {
        super(card);
    }

    @Override
    public ShadowMysteriousAssassin copy() {
        return new ShadowMysteriousAssassin(this);
    }
}

class ShadowMysteriousAssassinEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    ShadowMysteriousAssassinEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another nonland permanent. If you do, " +
                "draw two cards and each opponent loses life equal to the mana value of the sacrificed permanent";
    }

    private ShadowMysteriousAssassinEffect(final ShadowMysteriousAssassinEffect effect) {
        super(effect);
    }

    @Override
    public ShadowMysteriousAssassinEffect copy() {
        return new ShadowMysteriousAssassinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(0, 1, filter);
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        int mv = permanent.getManaValue();
        player.drawCards(2, source, game);
        if (mv < 1) {
            return true;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Optional.ofNullable(opponentId)
                    .map(game::getPlayer)
                    .ifPresent(opponent -> opponent.loseLife(mv, game, source, false));
        }
        return true;
    }
}
