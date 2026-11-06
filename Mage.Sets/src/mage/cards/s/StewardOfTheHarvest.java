package mage.cards.s;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author Jmlundeen
 */
public final class StewardOfTheHarvest extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land cards from your graveyard");

    public StewardOfTheHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, exile up to three target land cards from your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true));
        ability.addTarget(new TargetCardInYourGraveyard(0, 3, filter));
        this.addAbility(ability);

        // Creatures you control have all activated abilities of all land cards exiled with this creature.
        this.addAbility(new SimpleStaticAbility(new StewardOfTheHarvestEffect()));
    }

    private StewardOfTheHarvest(final StewardOfTheHarvest card) {
        super(card);
    }

    @Override
    public StewardOfTheHarvest copy() {
        return new StewardOfTheHarvest(this);
    }
}

class StewardOfTheHarvestEffect extends ContinuousEffectImpl {

    StewardOfTheHarvestEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Creatures you control have all activated abilities of all land cards exiled with this creature.";
    }

    private StewardOfTheHarvestEffect(final StewardOfTheHarvestEffect effect) {
        super(effect);
    }

    @Override
    public StewardOfTheHarvestEffect copy() {
        return new StewardOfTheHarvestEffect(this);
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
                .getCards(StaticFilters.FILTER_CARD_LAND, game)
                .stream()
                .map(card -> card.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(Ability::isActivatedAbility)
                .collect(Collectors.toSet());
        if (abilities.isEmpty()) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game)) {
            for (Ability ability : abilities) {
                permanent.addAbility(ability, source.getSourceId(), game, true);
            }
        }
        return true;
    }
}
