package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author North
 */
public final class SeverTheBloodline extends CardImpl {

    public SeverTheBloodline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Exile target creature and all other creatures with the same name as that creature.
        this.getSpellAbility().addEffect(new SeverTheBloodlineEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {5}{B}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{B}{B}")));
    }

    private SeverTheBloodline(final SeverTheBloodline card) {
        super(card);
    }

    @Override
    public SeverTheBloodline copy() {
        return new SeverTheBloodline(this);
    }
}

class SeverTheBloodlineEffect extends OneShotEffect {

    SeverTheBloodlineEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature and all other creatures with the same name as that creature";
    }

    private SeverTheBloodlineEffect(final SeverTheBloodlineEffect effect) {
        super(effect);
    }

    @Override
    public SeverTheBloodlineEffect copy() {
        return new SeverTheBloodlineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || targetPermanent == null) {
            return false;
        }
        Set<Card> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(targetPermanent, game))
                .collect(Collectors.toSet());
        set.add(targetPermanent);
        return player.moveCards(set, Zone.EXILED, source, game);
    }
}
