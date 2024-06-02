package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class EssenceReliquary extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another target permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EssenceReliquary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // {T}: Return another target permanent you control and all Auras you control attached to it to their owner's hand. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new EssenceReliquaryTargetEffect(),
                new TapSourceCost(), MyTurnCondition.instance
        );
        ability.addTarget(new TargetControlledPermanent(filter));
    }

    private EssenceReliquary(final EssenceReliquary card) {
        super(card);
    }

    @Override
    public EssenceReliquary copy() {
        return new EssenceReliquary(this);
    }
}

class EssenceReliquaryTargetEffect extends OneShotEffect {

    EssenceReliquaryTargetEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and all Auras you control attached to it to their owner's hand";
    }

    private EssenceReliquaryTargetEffect(final EssenceReliquaryTargetEffect effect) {
        super(effect);
    }

    @Override
    public EssenceReliquaryTargetEffect copy() {
        return new EssenceReliquaryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(permanent);
        cards.addAll(permanent.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.isControlledBy(source.getControllerId()))
                .filter(p -> p.hasSubtype(SubType.AURA, game))
                .map(Permanent::getId)
                .collect(Collectors.toList()));
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}