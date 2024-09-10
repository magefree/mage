package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;
import mage.util.SubTypes;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OneLastJob extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mount or Vehicle card from your graveyard");
    private static final FilterCard filter2 = new FilterCard("Aura or Equipment card");

    static {
        filter.add(Predicates.or(SubType.MOUNT.getPredicate(), SubType.VEHICLE.getPredicate()));
        filter2.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
    }

    public OneLastJob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2} -- Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(2));

        // + {1} -- Return target Mount or Vehicle card from your graveyard to the battlefield.
        this.getSpellAbility().addMode(
                new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect())
                        .addTarget(new TargetCardInYourGraveyard(filter))
                        .withCost(new GenericManaCost(1))
        );

        // + {1} -- Return target Aura or Equipment card from your graveyard to the battlefield attached to a creature you control.
        this.getSpellAbility().addMode(
                new Mode(new OneLastJobEffect())
                        .addTarget(new TargetCardInYourGraveyard(filter2))
                        .withCost(new GenericManaCost(1))
        );
    }

    private OneLastJob(final OneLastJob card) {
        super(card);
    }

    @Override
    public OneLastJob copy() {
        return new OneLastJob(this);
    }
}

// Inspired vaguely by Academy Researcher and Gryff's Boon
class OneLastJobEffect extends OneShotEffect {

    OneLastJobEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return target Aura or Equipment card from your graveyard to the battlefield attached to a creature you control";
    }

    private OneLastJobEffect(final OneLastJobEffect effect) {
        super(effect);
    }

    @Override
    public OneLastJobEffect copy() {
        return new OneLastJobEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card cardToReturn = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (cardToReturn == null || controller == null) {
            return false;
        }

        FilterPermanent filter = new FilterControlledCreaturePermanent(
                "a creature you control that " + cardToReturn.getLogName() + " can be attached to"
        );
        filter.add(new CanBeEnchantedByPredicate(cardToReturn));
        Target target = new TargetPermanent(filter);
        target.withNotTarget(true);
        controller.choose(Outcome.BoostCreature, target, source, game);

        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            game.informPlayers(
                    "Chosen creature to attach " + cardToReturn.getLogName()
                            + " to:" + permanent.getLogName()
                            + CardUtil.getSourceLogName(game, source)
            );
            game.getState().setValue("attachTo:" + cardToReturn.getId(), permanent);
            controller.moveCards(cardToReturn, Zone.BATTLEFIELD, source, game);
            permanent.addAttachment(cardToReturn.getId(), source, game);
            return true;
        }

        SubTypes subTypes = cardToReturn.getSubtype(game);
        if (subTypes.contains(SubType.AURA)) {
            // For Auras, we can only attempt to return if a valid choice was chosen
            game.informPlayers(
                    "No creatures controlled by " + controller.getLogName() +
                            " to return " + cardToReturn.getLogName() +
                            " attached to" + CardUtil.getSourceLogName(game, source)
            );
            return true;
        }

        // For non-Aura, card is returned not attached to anything.
        controller.moveCards(cardToReturn, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
