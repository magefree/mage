package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrzhovCharm extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 1 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public OrzhovCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        //Choose one - Return target creature you control and all Auras you control attached to it to their owner's hand
        this.getSpellAbility().addEffect(new OrzhovCharmReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // or destroy target creature and you lose life equal to its toughness;
        Mode mode = new Mode(new OrzhovCharmDestroyAndLoseLifeEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // or return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode);
    }

    private OrzhovCharm(final OrzhovCharm card) {
        super(card);
    }

    @Override
    public OrzhovCharm copy() {
        return new OrzhovCharm(this);
    }
}

class OrzhovCharmReturnToHandEffect extends OneShotEffect {

    OrzhovCharmReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and all Auras you control attached to it to their owner's hand";
    }

    private OrzhovCharmReturnToHandEffect(final OrzhovCharmReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovCharmReturnToHandEffect copy() {
        return new OrzhovCharmReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(permanent);
        permanent.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.isControlledBy(source.getControllerId()))
                .filter(p -> p.hasSubtype(SubType.AURA, game))
                .forEach(cards::add);
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}

class OrzhovCharmDestroyAndLoseLifeEffect extends OneShotEffect {

    OrzhovCharmDestroyAndLoseLifeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy target creature and you lose life equal to its toughness";
    }

    private OrzhovCharmDestroyAndLoseLifeEffect(final OrzhovCharmDestroyAndLoseLifeEffect effect) {
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
        if (target == null || controller == null) {
            return false;
        }
        int toughness = target.getToughness().getValue();
        target.destroy(source, game, false);
        if (toughness > 0) {
            controller.loseLife(toughness, game, source, false);
        }
        return true;
    }
}
