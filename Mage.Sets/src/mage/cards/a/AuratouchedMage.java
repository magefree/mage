
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToLKIPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class AuratouchedMage extends CardImpl {

    public AuratouchedMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Auratouched Mage enters the battlefield, search your library for an Aura card that could enchant it. If Auratouched Mage is still on the battlefield, put that Aura card onto the battlefield attached to it. Otherwise, reveal the Aura card and put it into your hand. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AuratouchedMageEffect(), false));

    }

    private AuratouchedMage(final AuratouchedMage card) {
        super(card);
    }

    @Override
    public AuratouchedMage copy() {
        return new AuratouchedMage(this);
    }
}

class AuratouchedMageEffect extends OneShotEffect {

    public AuratouchedMageEffect() {
        super(Outcome.BoostCreature);
        staticText = "search your library for an Aura card that could enchant it. If {this} is still on the battlefield, put that Aura card onto the battlefield attached to it. Otherwise, reveal the Aura card and put it into your hand. Then shuffle";
    }

    private AuratouchedMageEffect(final AuratouchedMageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            FilterCard filter = new FilterCard("aura that could enchant " + source.getSourceObject(game).getName());
            filter.add(SubType.AURA.getPredicate());
            filter.add(new AuraCardCanAttachToLKIPermanentId(source.getSourceId()));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            target.withNotTarget(true);
            if (controller.searchLibrary(target, source, game)) {
                if (target.getFirstTarget() != null) {
                    Card aura = game.getCard(target.getFirstTarget());
                    Permanent auratouchedMage = source.getSourcePermanentIfItStillExists(game);
                    if (aura != null && auratouchedMage != null
                            && game.getState().getZoneChangeCounter(source.getSourceId()) == source.getSourceObjectZoneChangeCounter()) {
                        game.getState().setValue("attachTo:" + aura.getId(), auratouchedMage);
                        if (controller.moveCards(aura, Zone.BATTLEFIELD, source, game)) {
                            auratouchedMage.addAttachment(aura.getId(), source, game);
                        }
                    } else {
                        Cards auraRevealed = new CardsImpl(aura);
                        controller.revealCards(source, auraRevealed, game);
                        controller.moveCards(aura, Zone.HAND, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public AuratouchedMageEffect copy() {
        return new AuratouchedMageEffect(this);
    }
}
