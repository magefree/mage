package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SqueeDubiousMonarch extends CardImpl {

    public SqueeDubiousMonarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Squee, Dubious Monarch attacks, create a 1/1 red Goblin creature token that's tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
                new GoblinToken(), 1, true, true
        )));

        // You may cast Squee, Dubious Monarch from your graveyard by paying {3}{R} and exiling four other cards from your graveyard rather than paying its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SqueeDubiousMonarchEffect()));
    }

    private SqueeDubiousMonarch(final SqueeDubiousMonarch card) {
        super(card);
    }

    @Override
    public SqueeDubiousMonarch copy() {
        return new SqueeDubiousMonarch(this);
    }
}

class SqueeDubiousMonarchEffect extends AsThoughEffectImpl {

    private static final FilterCard filter = new FilterCard("other cards from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SqueeDubiousMonarchEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "you may cast {this} from your graveyard by paying {3}{R} and exiling " +
                "four other cards from your graveyard rather than paying its mana cost";
    }

    private SqueeDubiousMonarchEffect(final SqueeDubiousMonarchEffect effect) {
        super(effect);
    }

    @Override
    public SqueeDubiousMonarchEffect copy() {
        return new SqueeDubiousMonarchEffect(this);
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
        costs.add(new ExileFromGraveCost(new TargetCardInYourGraveyard(4, filter)));
        controller.setCastSourceIdWithAlternateMana(objectId, new ManaCostsImpl<>("{3}{R}"), costs);
        return true;
    }
}
