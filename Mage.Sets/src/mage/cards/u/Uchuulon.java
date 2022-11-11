package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Uchuulon extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("Crabs, Oozes, and/or Horrors you control");

    static {
        filter.add(Predicates.or(
                SubType.CRAB.getPredicate(),
                SubType.OOZE.getPredicate(),
                SubType.HORROR.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public Uchuulon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Uchuulon's power is equal to the number of Crabs, Oozes, and/or Horrors you control.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerSourceEffect(xValue, Duration.EndOfGame)));

        // Horrific Symbiosis — At the beginning of your end step, exile up to one target creature card from an opponent's graveyard. If you, create a token that's a copy of Uchuulon.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new UchuulonEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInOpponentsGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        ));
        this.addAbility(ability.withFlavorWord("Horrific Symbiosis"));
    }

    private Uchuulon(final Uchuulon card) {
        super(card);
    }

    @Override
    public Uchuulon copy() {
        return new Uchuulon(this);
    }
}

class UchuulonEffect extends OneShotEffect {

    UchuulonEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target creature card from an opponent's graveyard. " +
                "If you, create a token that's a copy of {this}";
    }

    private UchuulonEffect(final UchuulonEffect effect) {
        super(effect);
    }

    @Override
    public UchuulonEffect copy() {
        return new UchuulonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        new CreateTokenCopySourceEffect().apply(game, source);
        return true;
    }
}
