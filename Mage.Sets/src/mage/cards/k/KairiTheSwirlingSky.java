package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KairiTheSwirlingSky extends CardImpl {

    public KairiTheSwirlingSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}"), false));

        // When Kairi, the Swirling Sky dies, choose one —
        // • Return any number of target nonland permanents with total mana value 6 or less to their owners' hands.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return any number of target nonland permanents with total mana value 6 or less to their owners' hands"));
        ability.addTarget(new KairiTheSwirlingSkyTarget());

        // • Mill six cards, then return up to two instant and/or sorcery cards from your graveyard to your hand.
        ability.addMode(new Mode(new KairiTheSwirlingSkyEffect()));
        this.addAbility(ability);
    }

    private KairiTheSwirlingSky(final KairiTheSwirlingSky card) {
        super(card);
    }

    @Override
    public KairiTheSwirlingSky copy() {
        return new KairiTheSwirlingSky(this);
    }
}

class KairiTheSwirlingSkyTarget extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanents with total mana value 6 or less");

    KairiTheSwirlingSkyTarget() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private KairiTheSwirlingSkyTarget(final KairiTheSwirlingSkyTarget target) {
        super(target);
    }

    @Override
    public KairiTheSwirlingSkyTarget copy() {
        return new KairiTheSwirlingSkyTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }
        return permanent.getManaValue()
                + this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum() <= 6;
    }
}

class KairiTheSwirlingSkyEffect extends OneShotEffect {

    KairiTheSwirlingSkyEffect() {
        super(Outcome.ReturnToHand);
        staticText = "mill six cards, then return up to two instant " +
                "and/or sorcery cards from your graveyard to your hand";
    }

    private KairiTheSwirlingSkyEffect(final KairiTheSwirlingSkyEffect effect) {
        super(effect);
    }

    @Override
    public KairiTheSwirlingSkyEffect copy() {
        return new KairiTheSwirlingSkyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(6, source, game);
        TargetCard target = new TargetCardInGraveyard(
                0, 2, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
        target.setNotTarget(true);
        player.choose(outcome, player.getGraveyard(), target, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
    }
}
