
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author anonymous
 */
public final class Metalworker extends CardImpl {

    public Metalworker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Reveal any number of artifact cards in your hand. Add {C}{C} for each card revealed this way.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new MetalworkerManaEffect(), new TapSourceCost()));
    }

    public Metalworker(final Metalworker card) {
        super(card);
    }

    @Override
    public Metalworker copy() {
        return new Metalworker(this);
    }
}

class MetalworkerManaEffect extends ManaEffect {

    public MetalworkerManaEffect() {
        super();
        staticText = "Reveal any number of artifact cards in your hand. Add {C}{C} for each card revealed this way";
    }

    public MetalworkerManaEffect(final MetalworkerManaEffect effect) {
        super(effect);
    }

    @Override
    public MetalworkerManaEffect copy() {
        return new MetalworkerManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        checkToFirePossibleEvents(getMana(game, source), game, source);
        controller.getManaPool().addMana(getMana(game, source), game, source);
        return true;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }
        int artifacts = controller.getHand().count(StaticFilters.FILTER_CARD_ARTIFACT, game);
        if (netMana) {
            return Mana.ColorlessMana(artifacts * 2);
        }
        if (artifacts > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_ARTIFACT);
            if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(source, cards, game);
                return Mana.ColorlessMana(target.getTargets().size() * 2);
            }
        }
        return new Mana();
    }

}
