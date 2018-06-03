
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class GodPharaohsGift extends CardImpl {

    public GodPharaohsGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // At the beginning of combat on your turn, you may exile a creature card from your graveyard. If you do, create a token that's a copy of that card, except it's a 4/4 black Zombie. It gains haste until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, new GodPharaohsGiftEffect(), TargetController.YOU, true, false));

    }

    public GodPharaohsGift(final GodPharaohsGift card) {
        super(card);
    }

    @Override
    public GodPharaohsGift copy() {
        return new GodPharaohsGift(this);
    }
}

class GodPharaohsGiftEffect extends OneShotEffect {

    private final UUID exileId = UUID.randomUUID();

    public GodPharaohsGiftEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may exile a creature card from your graveyard. If you do, create a token that's a copy of that card, except it's a 4/4 black Zombie. It gains haste until end of turn";
    }

    public GodPharaohsGiftEffect(final GodPharaohsGiftEffect effect) {
        super(effect);
    }

    @Override
    public GodPharaohsGiftEffect copy() {
        return new GodPharaohsGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.setNotTarget(true);
            if (!controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, game).isEmpty()
                    && controller.choose(Outcome.PutCreatureInPlay, target, source.getId(), game)) {
                Card cardChosen = game.getCard(target.getFirstTarget());
                if (cardChosen != null
                        && cardChosen.moveToExile(exileId, sourceObject.getIdName(), source.getSourceId(), game)) {
                    EmptyToken token = new EmptyToken();
                    CardUtil.copyTo(token).from(cardChosen);
                    token.removePTCDA();
                    token.getPower().modifyBaseValue(4);
                    token.getToughness().modifyBaseValue(4);
                    token.getColor(game).setColor(ObjectColor.BLACK);
                    token.getSubtype(game).clear();
                    token.getSubtype(game).add(SubType.ZOMBIE);
                    if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
                        Permanent tokenPermanent = game.getPermanent(token.getLastAddedToken());
                        if (tokenPermanent != null) {
                            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                            effect.setTargetPointer(new FixedTarget(tokenPermanent.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
