
package mage.cards.r;

import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2, Susucr
 */
public final class RisenExecutioner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie creatures");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public RisenExecutioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Risen Executioner can't block.
        this.addAbility(new CantBlockAbility());

        // Other Zombie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // You may cast Risen Executioner from your graveyard if you pay {1} more to cast it for each other creature card in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new RisenExecutionerCastEffect())
                .setIdentifier(MageIdentifier.RisenExectutionerAlternateCast);
        this.addAbility(ability);

    }

    private RisenExecutioner(final RisenExecutioner card) {
        super(card);
    }

    @Override
    public RisenExecutioner copy() {
        return new RisenExecutioner(this);
    }
}

class RisenExecutionerCastEffect extends AsThoughEffectImpl {

    protected static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(AnotherPredicate.instance);
    }

    RisenExecutionerCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard if you pay {1} more to cast it for each other creature card in your graveyard";
    }

    private RisenExecutionerCastEffect(final RisenExecutionerCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RisenExecutionerCastEffect copy() {
        return new RisenExecutionerCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!sourceId.equals(source.getSourceId())) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if(card == null
                || !card.isOwnedBy(affectedControllerId)
                || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        if(controller == null) {
            return false;
        }
        int costIncrease = controller.getGraveyard().count(filter, source.getControllerId(), source, game);
        ManaCosts<ManaCost> adjustedCost = CardUtil.adjustCost(card.getSpellAbility().getManaCostsToPay(), -costIncrease);
        controller.setCastSourceIdWithAlternateMana(card.getId(), adjustedCost, null, MageIdentifier.RisenExectutionerAlternateCast);
        return true;
    }
}