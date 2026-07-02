package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class MoonstoneHarshMistress extends CardImpl {

    public MoonstoneHarshMistress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.subtype.add(SubType.VILLAIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you discard a card, you may exile that card from your graveyard. If you do, until the end of your next turn, you may play that card.
        this.addAbility(new DiscardCardControllerTriggeredAbility(new MoonstoneHarshMistressEffect(), true));
    }

    private MoonstoneHarshMistress(final MoonstoneHarshMistress card) {
        super(card);
    }

    @Override
    public MoonstoneHarshMistress copy() {
        return new MoonstoneHarshMistress(this);
    }
}

class MoonstoneHarshMistressEffect extends OneShotEffect {

    MoonstoneHarshMistressEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card from your graveyard. If you do, "
            + "until the end of your next turn, you may play that card";
    }

    private MoonstoneHarshMistressEffect(final MoonstoneHarshMistressEffect effect) {
        super(effect);
    }

    @Override
    public MoonstoneHarshMistressEffect copy() {
        return new MoonstoneHarshMistressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = (Card) getValue("discardedCard");
        if (card == null || player == null) {
            return false;
        }

        player.moveCardsToExile(
            card, source, game, true,
            CardUtil.getExileZoneId(game, source),
            CardUtil.getSourceName(game, source)
        );

        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED,Duration.UntilEndOfYourNextTurn)
            .setTargetPointer(new FixedTarget(card.getId(), game)), source);
        return true;
    }
}
