package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FellGravship extends CardImpl {

    public FellGravship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, mill three cards, then return a creature or Spacecraft card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new FellGravshipEffect());
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // Lifelink
        // 3/2
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(LifelinkAbility.getInstance())
                .withPT(3, 2));
    }

    private FellGravship(final FellGravship card) {
        super(card);
    }

    @Override
    public FellGravship copy() {
        return new FellGravship(this);
    }
}

class FellGravshipEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or Spacecraft card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.SPACECRAFT.getPredicate()
        ));
    }

    FellGravshipEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a creature or Spacecraft card from your graveyard to your hand";
    }

    private FellGravshipEffect(final FellGravshipEffect effect) {
        super(effect);
    }

    @Override
    public FellGravshipEffect copy() {
        return new FellGravshipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.withNotTarget(true);
        player.choose(outcome, player.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
