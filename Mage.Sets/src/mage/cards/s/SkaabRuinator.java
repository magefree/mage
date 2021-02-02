
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Alvin
 */
public final class SkaabRuinator extends CardImpl {

    public SkaabRuinator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // As an additional cost to cast Skaab Ruinator, exile three creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, 3, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast Skaab Ruinator from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new SkaabRuinatorPlayEffect()));
    }

    private SkaabRuinator(final SkaabRuinator card) {
        super(card);
    }

    @Override
    public SkaabRuinator copy() {
        return new SkaabRuinator(this);
    }
}

class SkaabRuinatorPlayEffect extends AsThoughEffectImpl {

    public SkaabRuinatorPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard";
    }

    public SkaabRuinatorPlayEffect(final SkaabRuinatorPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SkaabRuinatorPlayEffect copy() {
        return new SkaabRuinatorPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(source.getSourceId())
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}
