
package mage.cards.o;

import java.util.LinkedList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OrzhovCharm extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with converted mana cost 1 or less from your graveyard");
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 2));
    }

    public OrzhovCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{B}");


        //Choose one - Return target creature you control and all Auras you control attached to it to their owner's hand
        this.getSpellAbility().addEffect(new OrzhovCharmReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // or destroy target creature and you lose life equal to its toughness;
        Mode mode = new Mode();
        mode.getEffects().add(new OrzhovCharmDestroyAndLoseLifeEffect());
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // or return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        Mode mode2 = new Mode();
        mode2.getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode2.getTargets().add(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode2);


    }

    public OrzhovCharm(final OrzhovCharm card) {
        super(card);
    }

    @Override
    public OrzhovCharm copy() {
        return new OrzhovCharm(this);
    }
}

class OrzhovCharmReturnToHandEffect extends OneShotEffect {

    public OrzhovCharmReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and all Auras you control attached to it to their owner's hand";
    }

    public OrzhovCharmReturnToHandEffect(final OrzhovCharmReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovCharmReturnToHandEffect copy() {
        return new OrzhovCharmReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            LinkedList<UUID> attachments = new LinkedList<>();
            attachments.addAll(target.getAttachments());
            for (UUID attachmentId : attachments) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.isControlledBy(source.getControllerId())
                        && attachment.hasSubtype(SubType.AURA, game)) {
                    attachment.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
            if (target.isControlledBy(source.getControllerId())) {
                target.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}

class OrzhovCharmDestroyAndLoseLifeEffect extends OneShotEffect {

    public OrzhovCharmDestroyAndLoseLifeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy target creature and you lose life equal to its toughness";
    }

    public OrzhovCharmDestroyAndLoseLifeEffect(final OrzhovCharmDestroyAndLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovCharmDestroyAndLoseLifeEffect copy() {
        return new OrzhovCharmDestroyAndLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (target != null && controller != null) {
            int toughness = target.getToughness().getValue();
            target.destroy(source.getSourceId(), game, false);
            if (toughness > 0) {
                controller.loseLife(toughness, game, false);
            }
            return true;
        }
        return false;
    }
}
