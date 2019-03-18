
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class WispweaverAngel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WispweaverAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wispweaver Angel enters the battlefield, you may exile another target creature you control, then return that card to the battlefield under its owner's control.
        Effect effect = new ExileTargetForSourceEffect();
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1, filter, false));
        ability.addEffect(new WispweaverAngelEffect());
        this.addAbility(ability);
    }

    public WispweaverAngel(final WispweaverAngel card) {
        super(card);
    }

    @Override
    public WispweaverAngel copy() {
        return new WispweaverAngel(this);
    }
}

class WispweaverAngelEffect extends OneShotEffect {

    WispweaverAngelEffect() {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under its owner's control";
    }

    WispweaverAngelEffect(final WispweaverAngelEffect effect) {
        super(effect);
    }

    @Override
    public WispweaverAngelEffect copy() {
        return new WispweaverAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToBattlefield = new CardsImpl();
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileZoneId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                if (exileZone != null) {
                    for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                        if (exileZone.contains(targetId)) {
                            cardsToBattlefield.add(targetId);
                        } else {
                            Card card = game.getCard(targetId);
                            if (card instanceof MeldCard) {
                                MeldCard meldCard = (MeldCard) card;
                                Card topCard = meldCard.getTopHalfCard();
                                Card bottomCard = meldCard.getBottomHalfCard();
                                if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter() && exileZone.contains(topCard.getId())) {
                                    cardsToBattlefield.add(topCard);
                                }
                                if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter() && exileZone.contains(bottomCard.getId())) {
                                    cardsToBattlefield.add(bottomCard);
                                }
                            }
                        }
                    }
                }
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }
}
