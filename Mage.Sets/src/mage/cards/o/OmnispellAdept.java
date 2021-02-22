package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author TheElk801
 */
public final class OmnispellAdept extends CardImpl {

    public OmnispellAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{U}, {T}: You may cast an instant or sorcery card from your hand 
        // without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new OmnispellAdeptEffect(), new ManaCostsImpl("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private OmnispellAdept(final OmnispellAdept card) {
        super(card);
    }

    @Override
    public OmnispellAdept copy() {
        return new OmnispellAdept(this);
    }
}

class OmnispellAdeptEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card from your hand");

    public OmnispellAdeptEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an instant or sorcery card from your hand "
                + "without paying its mana cost";
    }

    public OmnispellAdeptEffect(final OmnispellAdeptEffect effect) {
        super(effect);
    }

    @Override
    public OmnispellAdeptEffect copy() {
        return new OmnispellAdeptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterCard realFilter = filter.copy();
        Target target = new TargetCardInHand(realFilter);
        // choose one card until it possible to cast
        if (target.canChoose(source.getSourceId(), controller.getId(), game)
                && controller.chooseUse(Outcome.PlayForFree, "Cast an instant or sorcery "
                + "card from your hand without paying its mana cost?", source, game)) {
            Card cardToCast;
            while (controller.canRespond()
                    && controller.chooseTarget(Outcome.PlayForFree, target, source, game)) {
                cardToCast = game.getCard(target.getFirstTarget());
                if (cardToCast == null) {
                    break;
                }
                realFilter.add(Predicates.not(new CardIdPredicate(cardToCast.getId()))); // remove card from choose dialog (infinite fix)

                if (!cardToCast.getSpellAbility().canChooseTarget(game, controller.getId())) {
                    continue;
                }

                game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
                Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(cardToCast, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);

                if (cardWasCast) {
                    break;
                } else {
                    game.informPlayer(controller, "You're not able to cast "
                            + cardToCast.getIdName() + " or you canceled the casting.");
                }
            }
        }
        return true;
    }
}
