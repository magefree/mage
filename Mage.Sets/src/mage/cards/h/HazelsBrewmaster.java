package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author PurpleCrowbar
 */
public final class HazelsBrewmaster extends CardImpl {

    public HazelsBrewmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SQUIRREL, SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Hazel's Brewmaster enters or attacks, exile up to one target card from a graveyard and create a Food token.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true));
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        ability.addEffect(new CreateTokenEffect(new FoodToken()).concatBy("and"));
        this.addAbility(ability);

        // Foods you control have all activated abilities of all creature cards exiled with Hazel's Brewmaster.
        this.addAbility(new SimpleStaticAbility(new HazelsBrewmasterAbilityEffect()));
    }

    private HazelsBrewmaster(final HazelsBrewmaster card) {
        super(card);
    }

    @Override
    public HazelsBrewmaster copy() {
        return new HazelsBrewmaster(this);
    }
}

class HazelsBrewmasterAbilityEffect extends ContinuousEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods you control");

    HazelsBrewmasterAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Foods you control have all activated abilities of all creature cards exiled with {this}";
    }

    private HazelsBrewmasterAbilityEffect(final HazelsBrewmasterAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())
        ));
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Set<Ability> abilities = exileZone
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                .stream()
                .map(card -> card.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(Ability::isActivatedAbility)
                .collect(Collectors.toSet());
        if (abilities.isEmpty()) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            for (Ability ability : abilities) {
                permanent.addAbility(ability, source.getSourceId(), game, true);
            }
        }
        return true;
    }

    @Override
    public HazelsBrewmasterAbilityEffect copy() {
        return new HazelsBrewmasterAbilityEffect(this);
    }
}
