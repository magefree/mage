package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class Helbrute extends CardImpl {

    public Helbrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{R}");

        this.subtype.add(SubType.ASTARTES, SubType.DREADNOUGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Sarcophagus — You may cast Helbrute from your graveyard by exiling another creature card from your graveyard in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new HelbruteEffect()).withFlavorWord("Sarcophagus"));
    }

    private Helbrute(final Helbrute card) {
        super(card);
    }

    @Override
    public Helbrute copy() {
        return new Helbrute(this);
    }
}

class HelbruteEffect extends AsThoughEffectImpl {

    private static final FilterCard filter = new FilterCard("another creature card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HelbruteEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "you may cast {this} from your graveyard by exiling another " +
                "creature card from your graveyard in addition to paying its other costs.";
    }

    private HelbruteEffect(final HelbruteEffect effect) {
        super(effect);
    }

    @Override
    public HelbruteEffect copy() {
        return new HelbruteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.getSourceId().equals(objectId)
                || !source.isControlledBy(affectedControllerId)
                || game.getState().getZone(objectId) != Zone.GRAVEYARD) {
            return false;
        }
        Player controller = game.getPlayer(affectedControllerId);
        if (controller == null) {
            return false;
        }
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        controller.setCastSourceIdWithAlternateMana(objectId, new ManaCostsImpl<>("{3}{B}{R}"), costs);
        return true;
    }
}
