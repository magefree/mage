package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class TonyStark extends ModalDoubleFacedCard {

    public TonyStark(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE},
            new SubType[]{SubType.HUMAN, SubType.ARTIFICER, SubType.HERO}, "{1}{U}",
            "The Invincible Iron Man",
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE},
            new SubType[]{SubType.HUMAN, SubType.HERO}, "{4}{U}{R}"
        );
        this.getLeftHalfCard().setPT(1, 3);
        this.getRightHalfCard().setPT(5, 5);

        // {1}, {T}: Look at the top four cards of your library. You may reveal an artifact card from among them
        // and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
            4, 1, StaticFilters.FILTER_CARD_ARTIFACT_AN, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // {4}{U}{R}: Transform Tony Stark. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
            new TransformSourceEffect(), new ManaCostsImpl<>("{4}{U}{R}")
        ));

        // The Invincible Iron Man
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // At the beginning of combat on your turn, you may put an artifact card from your hand onto the battlefield.
        // If it's an Equipment, attach it to The Invincible Iron Man.
        this.getRightHalfCard().addAbility(new BeginningOfCombatTriggeredAbility(new TheInvincibleIronManEffect()));
    }

    private TonyStark(final TonyStark card) {
        super(card);
    }

    @Override
    public TonyStark copy() {
        return new TonyStark(this);
    }
}

class TheInvincibleIronManEffect extends OneShotEffect {

    TheInvincibleIronManEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "you may put an artifact card from your hand onto the battlefield. " +
            "If it's an Equipment, attach it to {this}";
    }

    private TheInvincibleIronManEffect(final TheInvincibleIronManEffect effect) {
        super(effect);
    }

    @Override
    public TheInvincibleIronManEffect copy() {
        return new TheInvincibleIronManEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null
                || player.getHand().count(StaticFilters.FILTER_CARD_ARTIFACT_AN, source.getControllerId(), source, game) < 1
                || !player.chooseUse(Outcome.PutCardInPlay, "Put an artifact card from your hand onto the battlefield?", source, game)) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_ARTIFACT_AN);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent artifact = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        Permanent ironMan = source.getSourcePermanentIfItStillExists(game);
        if (artifact != null && ironMan != null && artifact.hasSubtype(SubType.EQUIPMENT, game)) {
            ironMan.addAttachment(artifact.getId(), source, game);
        }
        return true;
    }
}
