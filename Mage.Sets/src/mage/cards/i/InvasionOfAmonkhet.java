package mage.cards.i;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfAmonkhet extends TransformingDoubleFacedCard {

    public InvasionOfAmonkhet(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{1}{U}{B}",
                "Lazotep Convert",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE}, "BU"
        );
        this.getLeftHalfCard().setStartingDefense(4);
        this.getRightHalfCard().setPT(4, 4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Amonkhet enters the battlefield, each player mills three cards, then each opponent discards a card and you draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER)
        );
        ability.addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT).concatBy(", then"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.getLeftHalfCard().addAbility(ability);

        // Lazotep Convert
        // You may have Lazotep Convert enter the battlefield as a copy of any creature card in a graveyard, except it's a 4/4 black Zombie in addition to its other types.
        this.getRightHalfCard().addAbility(new EntersBattlefieldAbility(new LazotepConvertCopyEffect(), true));
    }

    private InvasionOfAmonkhet(final InvasionOfAmonkhet card) {
        super(card);
    }

    @Override
    public InvasionOfAmonkhet copy() {
        return new InvasionOfAmonkhet(this);
    }
}

class LazotepConvertCopyEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {

        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.removePTCDA();
            blueprint.getPower().setModifiedBaseValue(4);
            blueprint.getToughness().setModifiedBaseValue(4);
            blueprint.addSubType(SubType.ZOMBIE);
            blueprint.getColor().setColor(ObjectColor.BLACK);
            return true;
        }
    };

    private static final FilterCard filter = new FilterCreatureCard("creature card in a graveyard");

    public LazotepConvertCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "as a copy of any creature card in a graveyard, " +
                "except it's a 4/4 black Zombie in addition to its other types";
    }

    public LazotepConvertCopyEffect(final LazotepConvertCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInGraveyard(0, 1, filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card copyFromCard = game.getCard(target.getFirstTarget());
        if (copyFromCard == null) {
            return true;
        }
        game.addEffect(new CopyEffect(
                Duration.Custom, copyFromCard, source.getSourceId()
        ).setApplier(applier), source);
        return true;
    }

    @Override
    public LazotepConvertCopyEffect copy() {
        return new LazotepConvertCopyEffect(this);
    }
}
