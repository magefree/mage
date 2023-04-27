
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ArachnusSpinner extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Spider you control");

    static {
        filter.add(SubType.SPIDER.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ArachnusSpinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Reach (This creature can block creatures with flying.)
        this.addAbility(ReachAbility.getInstance());
        // Tap an untapped Spider you control: Search your graveyard and/or library for a card named Arachnus Web and put it onto the battlefield attached to target creature. If you search your library this way, shuffle it.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ArachnusSpinnerEffect(),
                new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArachnusSpinner(final ArachnusSpinner card) {
        super(card);
    }

    @Override
    public ArachnusSpinner copy() {
        return new ArachnusSpinner(this);
    }
}

class ArachnusSpinnerEffect extends OneShotEffect {

    public ArachnusSpinnerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your graveyard and/or library for a card named Arachnus Web "
                + "and put it onto the battlefield attached to target creature. "
                + "If you search your library this way, shuffle";
    }

    public ArachnusSpinnerEffect(final ArachnusSpinnerEffect effect) {
        super(effect);
    }

    @Override
    public ArachnusSpinnerEffect copy() {
        return new ArachnusSpinnerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterCard filter = new FilterCard("card named Arachnus Web");
        filter.add(new NamePredicate("Arachnus Web"));

        Card card = null;
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard for Arachnus Web?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        if (card == null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            controller.shuffleLibrary(source, game);
        }
        if (card != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                game.getState().setValue("attachTo:" + card.getId(), permanent.getId());
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    permanent.addAttachment(card.getId(), source, game); // shouldn't this be done automatically by the logic using the "attachTo:" calue?
                }
            }
        }
        return true;
    }
}
