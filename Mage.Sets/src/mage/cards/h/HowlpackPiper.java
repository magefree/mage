package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlpackPiper extends TransformingDoubleFacedCard {

    public HowlpackPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{G}",
                "Wildsong Howler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Howlpack Piper
        this.getLeftHalfCard().setPT(2, 2);

        // This spell can't be countered.
        this.getLeftHalfCard().addAbility(new CantBeCounteredSourceAbility());

        // {1}{G}, {T}: You may put a creature card from your hand onto the battlefield. If it's a Wolf or Werewolf, untap Howlpack Piper. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new HowlpackPiperEffect(), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Wildsong Howler
        this.getRightHalfCard().setPT(4, 4);

        // Whenever this creature enters the battlefield or transforms into Wildsong Howler, look at the top six cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getRightHalfCard().addAbility(new TransformsOrEntersTriggeredAbility(
                new LookLibraryAndPickControllerEffect(6, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                false));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private HowlpackPiper(final HowlpackPiper card) {
        super(card);
    }

    @Override
    public HowlpackPiper copy() {
        return new HowlpackPiper(this);
    }
}

class HowlpackPiperEffect extends OneShotEffect {

    HowlpackPiperEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put a creature card from your hand onto the battlefield. " +
                "If it's a Wolf or Werewolf, untap {this}";
    }

    private HowlpackPiperEffect(final HowlpackPiperEffect effect) {
        super(effect);
    }

    @Override
    public HowlpackPiperEffect copy() {
        return new HowlpackPiperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || sourcePermanent == null) {
            return true;
        }
        if (permanent.hasSubtype(SubType.WOLF, game) || permanent.hasSubtype(SubType.WEREWOLF, game)) {
            sourcePermanent.untap(game);
        }
        return true;
    }
}
