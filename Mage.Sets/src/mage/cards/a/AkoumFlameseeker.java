
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class AkoumFlameseeker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Ally you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public AkoumFlameseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Discard a card. If you do, draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AkoumFlameseekerEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private AkoumFlameseeker(final AkoumFlameseeker card) {
        super(card);
    }

    @Override
    public AkoumFlameseeker copy() {
        return new AkoumFlameseeker(this);
    }
}

class AkoumFlameseekerEffect extends OneShotEffect {

    public AkoumFlameseekerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard a card. If you do, draw a card";
    }

    private AkoumFlameseekerEffect(final AkoumFlameseekerEffect effect) {
        super(effect);
    }

    @Override
    public AkoumFlameseekerEffect copy() {
        return new AkoumFlameseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = controller.discard(1, false, false, source, game);
            if (!cards.isEmpty()) {
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
