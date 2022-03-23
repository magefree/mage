
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class AcademyResearchers extends CardImpl {

    public AcademyResearchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Academy Researchers enters the battlefield, you may put an Aura card from your hand onto the battlefield attached to Academy Researchers.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AcademyResearchersEffect(), true));
    }

    private AcademyResearchers(final AcademyResearchers card) {
        super(card);
    }

    @Override
    public AcademyResearchers copy() {
        return new AcademyResearchers(this);
    }
}

class AcademyResearchersEffect extends OneShotEffect {

    AcademyResearchersEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put an Aura card from your hand onto the battlefield attached to {this}.";
    }

    AcademyResearchersEffect(final AcademyResearchersEffect effect) {
        super(effect);
    }

    @Override
    public AcademyResearchersEffect copy() {
        return new AcademyResearchersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filterCardInHand = new FilterCard();
        filterCardInHand.add(SubType.AURA.getPredicate());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent academyResearchers = game.getPermanent(source.getSourceId());
        if (controller != null && academyResearchers != null) {
            filterCardInHand.add(new AuraCardCanAttachToPermanentId(academyResearchers.getId()));
            TargetCardInHand target = new TargetCardInHand(0, 1, filterCardInHand);
            if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                Card auraInHand = game.getCard(target.getFirstTarget());
                if (auraInHand != null) {
                    game.getState().setValue("attachTo:" + auraInHand.getId(), academyResearchers);
                    controller.moveCards(auraInHand, Zone.BATTLEFIELD, source, game);
                    if (academyResearchers.addAttachment(auraInHand.getId(), source, game)) {
                        game.informPlayers(controller.getLogName() + " put " + auraInHand.getLogName() + " on the battlefield attached to " + academyResearchers.getLogName() + '.');
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
