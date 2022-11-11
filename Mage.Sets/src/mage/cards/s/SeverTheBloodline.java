package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

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

    public SeverTheBloodlineEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature and all other creatures with the same name as that creature";
    }

    public SeverTheBloodlineEffect(final SeverTheBloodlineEffect effect) {
        super(effect);
    }

    @Override
    public SeverTheBloodlineEffect copy() {
        return new SeverTheBloodlineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (controller != null && targetPermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            if (CardUtil.haveEmptyName(targetPermanent)) {
                filter.add(new PermanentIdPredicate(targetPermanent.getId()));  // if no name (face down creature) only the creature itself is selected
            } else {
                filter.add(new NamePredicate(targetPermanent.getName()));
            }
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                controller.moveCardToExileWithInfo(permanent, null, "", source, game, Zone.BATTLEFIELD, true);
            }
            return true;
        }
        return false;
    }
}
