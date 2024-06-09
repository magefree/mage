package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class ShilgengarSireOfFamine extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BLOOD, "Blood tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public ShilgengarSireOfFamine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice another creature: Create a Blood token. If you sacrificed an Angel this way, create a number of Blood tokens equal to its toughness instead.
        this.addAbility(new SimpleActivatedAbility(
                new ConditionalOneShotEffect(
                        new CreateTokenEffect(new BloodToken(), SacrificeCostCreaturesToughness.instance),
                        new CreateTokenEffect(new BloodToken()),
                        ShilgengarSireOfFamineCondition.instance,
                        "create a Blood token. If you sacrificed an Angel this way, create a number of Blood tokens equal to its toughness instead"
                ),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));

        // {W/B}{W/B}{W/B}, Sacrifice six Blood tokens: Return each creature card from your graveyard to the battlefield with a finality counter on it. Those creatures are Vampires in addition to their other types.
        Ability ability = new SimpleActivatedAbility(
                new ShilgengarSireOfFamineEffect(),
                new ManaCostsImpl<>("{W/B}{W/B}{W/B}")
        );
        ability.addCost(new SacrificeTargetCost(6, filter));
        this.addAbility(ability);
    }

    private ShilgengarSireOfFamine(final ShilgengarSireOfFamine card) {
        super(card);
    }

    @Override
    public ShilgengarSireOfFamine copy() {
        return new ShilgengarSireOfFamine(this);
    }
}

enum ShilgengarSireOfFamineCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .anyMatch(p -> p.getSubtype(game).contains(SubType.ANGEL));
    }

    @Override
    public String toString() {
        return "you sacrificed an Angel this way";
    }
}

class ShilgengarSireOfFamineEffect extends OneShotEffect {

    ShilgengarSireOfFamineEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return each creature card from your graveyard to the battlefield with a finality counter on it. "
                + "Those creatures are Vampires in addition to their other types.";
    }

    private ShilgengarSireOfFamineEffect(final ShilgengarSireOfFamineEffect effect) {
        super(effect);
    }

    @Override
    public ShilgengarSireOfFamineEffect copy() {
        return new ShilgengarSireOfFamineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        OneShotEffect effect = new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                CounterType.FINALITY.createInstance()
        );
        effect.setTargetPointer(new FixedTargets(cards, game));
        effect.apply(game, source);
        game.getState().processAction(game);
        List<Permanent> permanents = cards
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!permanents.isEmpty()) {
            game.addEffect(new AddCardSubTypeTargetEffect(
                    SubType.VAMPIRE, Duration.Custom
            ).setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        return true;
    }
}