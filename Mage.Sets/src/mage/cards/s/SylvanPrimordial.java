
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class SylvanPrimordial extends CardImpl {

    public SylvanPrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Sylvan Primordial enters the battlefield, for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SylvanPrimordialEffect(),false));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for(UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterPermanent filter = new FilterPermanent("noncreature permanent from opponent " + opponent.getLogName());
                    filter.add(new ControllerIdPredicate(opponentId));
                    filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
                    TargetPermanent target = new TargetPermanent(0,1, filter,false);
                    ability.addTarget(target);
                }
            }
        }
    }

    public SylvanPrimordial(final SylvanPrimordial card) {
        super(card);
    }

    @Override
    public SylvanPrimordial copy() {
        return new SylvanPrimordial(this);
    }
}

class SylvanPrimordialEffect extends OneShotEffect {

    private static final FilterLandCard filterForest = new FilterLandCard("Forest");
    static{
        filterForest.add(new SubtypePredicate(SubType.FOREST));
    }

    public SylvanPrimordialEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library";
    }

    public SylvanPrimordialEffect(final SylvanPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public SylvanPrimordialEffect copy() {
        return new SylvanPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        int destroyedCreatures = 0;
        for (Target target: source.getTargets()) {
            if (target instanceof TargetPermanent) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                if (targetPermanent != null) {
                    if (targetPermanent.destroy(source.getSourceId(), game, false)) {
                        destroyedCreatures++;
                    }
                }
            }
        }
        if (destroyedCreatures > 0) {
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(destroyedCreatures,filterForest), true, true).apply(game, source);
        }
        return result;
    }
}

