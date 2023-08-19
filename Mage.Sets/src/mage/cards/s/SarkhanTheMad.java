
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonToken2;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class SarkhanTheMad extends CardImpl {

    public SarkhanTheMad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);
        this.setStartingLoyalty(7);

        this.addAbility(new LoyaltyAbility(new SarkhanTheMadRevealAndDrawEffect(), 0));

        Target targetCreature = new TargetCreaturePermanent();
        Ability sacAbility = new LoyaltyAbility(new SarkhanTheMadSacEffect(), -2);
        sacAbility.addTarget(targetCreature);
        this.addAbility(sacAbility);

        Ability damageAbility = new LoyaltyAbility(new SarkhanTheMadDragonDamageEffect(), -4);
        damageAbility.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(damageAbility);
    }

    private SarkhanTheMad(final SarkhanTheMad card) {
        super(card);
    }

    @Override
    public SarkhanTheMad copy() {
        return new SarkhanTheMad(this);
    }
}

class SarkhanTheMadRevealAndDrawEffect extends OneShotEffect {

    private static final String effectText = "Reveal the top card of your library and put it into your hand. {this} deals damage to himself equal to that card's mana value";

    SarkhanTheMadRevealAndDrawEffect() {
        super(Outcome.DrawCard);
        staticText = effectText;
    }

    SarkhanTheMadRevealAndDrawEffect(SarkhanTheMadRevealAndDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
                if (sourcePermanent != null) {
                    sourcePermanent.damage(card.getManaValue(), source.getSourceId(), source, game, false, false);
                }
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public SarkhanTheMadRevealAndDrawEffect copy() {
        return new SarkhanTheMadRevealAndDrawEffect(this);
    }

}

class SarkhanTheMadSacEffect extends OneShotEffect {

    private static final String effectText = "Target creature's controller sacrifices it, then that player creates a 5/5 red Dragon creature token with flying";

    SarkhanTheMadSacEffect() {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    SarkhanTheMadSacEffect(SarkhanTheMadSacEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (permanent != null) {
            permanent.sacrifice(source, game);

            Player player = game.getPlayer(permanent.getControllerId());
            if(player != null) {
                Token dragonToken = new DragonToken2();
                dragonToken.putOntoBattlefield(1, game, source, player.getId());
            }
        }
        return false;
    }

    @Override
    public SarkhanTheMadSacEffect copy() {
        return new SarkhanTheMadSacEffect(this);
    }
}

class SarkhanTheMadDragonDamageEffect extends OneShotEffect {

    private static final String effectText = "Each Dragon creature you control deals damage equal to its power to target player or planeswalker";
    private static final FilterControlledPermanent filter;

    static {
        filter = new FilterControlledPermanent();
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(SubType.DRAGON.getPredicate());
    }

    SarkhanTheMadDragonDamageEffect() {
        super(Outcome.Damage);
        staticText = effectText;
    }

    SarkhanTheMadDragonDamageEffect(SarkhanTheMadDragonDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> dragons = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        if (dragons != null && !dragons.isEmpty()) {
            for (Permanent dragon : dragons) {
                game.damagePlayerOrPermanent(source.getFirstTarget(), dragon.getPower().getValue(), dragon.getId(), source, game, false, true);
            }
            return true;
        }

        return false;
    }

    @Override
    public SarkhanTheMadDragonDamageEffect copy() {
        return new SarkhanTheMadDragonDamageEffect(this);
    }

}
