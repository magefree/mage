package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AssimilateTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class AssimilationAssessor extends CardImpl {

    public AssimilationAssessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters, exile target opponent's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllTargetPlayerEffect(true));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever this creature attacks, assimilate a creature card exiled with it.
        this.addAbility(new AttacksTriggeredAbility(new AssimilationAssessorEffect()));
    }

    private AssimilationAssessor(final AssimilationAssessor card) {
        super(card);
    }

    @Override
    public AssimilationAssessor copy() {
        return new AssimilationAssessor(this);
    }
}

class AssimilationAssessorEffect extends OneShotEffect {

    AssimilationAssessorEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "assimilate a creature card exiled with it. "
                + "<i>(Put it onto the battlefield under your control with a +1/+1 counter. "
                + "It's a Borg artifact creature and loses all other creature types.)</i>";
    }

    private AssimilationAssessorEffect(final AssimilationAssessorEffect effect) {
        super(effect);
    }

    @Override
    public AssimilationAssessorEffect copy() {
        return new AssimilationAssessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (controller == null || exileZone == null) {
            return false;
        }
        Cards creatureCards = new CardsImpl(exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        Card card;
        switch (creatureCards.size()) {
            case 0:
                return false;
            case 1:
                card = creatureCards.getCards(game).iterator().next();
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileZone.getId());
                target.withNotTarget(true);
                target.withChooseHint("to assimilate");
                controller.choose(outcome, creatureCards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        return AssimilateTargetEffect.assimilateCard(controller, card, source, game);
    }
}
