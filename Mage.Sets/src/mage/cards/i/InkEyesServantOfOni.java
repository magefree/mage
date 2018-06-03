
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class InkEyesServantOfOni extends CardImpl {

    public InkEyesServantOfOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        addSuperType(SuperType.LEGENDARY);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ninjutsu {3}{B}{B} ({3}{B}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{3}{B}{B}")));

        // Whenever Ink-Eyes, Servant of Oni deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new InkEyesServantOfOniEffect(), true, true));

        // {1}{B}: Regenerate Ink-Eyes.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{1}{B}")));
    }

    public InkEyesServantOfOni(final InkEyesServantOfOni card) {
        super(card);
    }

    @Override
    public InkEyesServantOfOni copy() {
        return new InkEyesServantOfOni(this);
    }
}

class InkEyesServantOfOniEffect extends OneShotEffect {

    public InkEyesServantOfOniEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put target creature card from that player's graveyard onto the battlefield under your control";
    }

    public InkEyesServantOfOniEffect(final InkEyesServantOfOniEffect effect) {
        super(effect);
    }

    @Override
    public InkEyesServantOfOniEffect copy() {
        return new InkEyesServantOfOniEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player you = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard("creature in that player's graveyard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        if (target.canChoose(source.getSourceId(), you.getId(), game)) {
            if (you.chooseTarget(Outcome.PutCreatureInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    you.moveCards(card, Zone.BATTLEFIELD, source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
