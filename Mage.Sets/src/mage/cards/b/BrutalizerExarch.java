
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class BrutalizerExarch extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public BrutalizerExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Brutalizer Exarch enters the battlefield, choose one
        // - Search your library for a creature card, reveal it, then shuffle your library and put that card on top of it;
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterCreatureCard("a creature card"));
        Ability ability = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(target, true, true), false);
        // or put target noncreature permanent on the bottom of its owner's library.
        Mode mode = new Mode(new BrutalizerExarchEffect2());
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private BrutalizerExarch(final BrutalizerExarch card) {
        super(card);
    }

    @Override
    public BrutalizerExarch copy() {
        return new BrutalizerExarch(this);
    }
}

class BrutalizerExarchEffect2 extends OneShotEffect {

    public BrutalizerExarchEffect2() {
        super(Outcome.Removal);
        this.staticText = "put target noncreature permanent on the bottom of its owner's library";
    }

    public BrutalizerExarchEffect2(final BrutalizerExarchEffect2 effect) {
        super(effect);
    }

    @Override
    public BrutalizerExarchEffect2 copy() {
        return new BrutalizerExarchEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            return controller.putCardsOnBottomOfLibrary(permanent, game, source, true);
        }
        return false;
    }
}
