package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateXXTokenExiledEffectManaValueEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritXXToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeverancePriest extends CardImpl {

    public SeverancePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When this creature enters, target opponent reveals their hand. You may choose a nonland card from it. If you do, exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SeverancePriestEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When this creature leaves the battlefield, the exiled card's owner creates an X/X white Spirit creature token, where X is the mana value of the exiled card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new CreateXXTokenExiledEffectManaValueEffect(SpiritXXToken::new, "white Spirit")
        ));
    }

    private SeverancePriest(final SeverancePriest card) {
        super(card);
    }

    @Override
    public SeverancePriest copy() {
        return new SeverancePriest(this);
    }
}

class SeverancePriestEffect extends OneShotEffect {

    SeverancePriestEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals their hand. You may choose " +
                "a nonland card from it. If you do, exile that card";
    }

    private SeverancePriestEffect(final SeverancePriestEffect effect) {
        super(effect);
    }

    @Override
    public SeverancePriestEffect copy() {
        return new SeverancePriestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(Outcome.Discard, opponent.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}
