package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */
public final class MagnigothTreefolk extends CardImpl {

    public MagnigothTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Domain - For each basic land type among lands you control, Magnigoth Treefolk has landwalk of that type.
        this.addAbility(new SimpleStaticAbility(new MagnigothTreefolkEffect()).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private MagnigothTreefolk(final MagnigothTreefolk card) {
        super(card);
    }

    @Override
    public MagnigothTreefolk copy() {
        return new MagnigothTreefolk(this);
    }
}

class MagnigothTreefolkEffect extends ContinuousEffectImpl {

    MagnigothTreefolkEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "for each basic land type among lands you control, {this} has landwalk of that type";
    }

    private MagnigothTreefolkEffect(final MagnigothTreefolkEffect effect) {
        super(effect);
    }

    @Override
    public MagnigothTreefolkEffect copy() {
        return new MagnigothTreefolkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        List<SubType> landTypes = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS,
                        source.getControllerId(), source.getSourceId(), game
                ).stream()
                .map(p -> SubType
                        .getBasicLands()
                        .stream()
                        .filter(subType -> p.hasSubtype(subType, game))
                        .collect(Collectors.toSet()))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        if (landTypes.isEmpty()) {
            return false;
        }
        for (SubType subType : landTypes) {
            switch (subType) {
                case PLAINS:
                    permanent.addAbility(new PlainswalkAbility(), source.getSourceId(), game);
                    break;
                case ISLAND:
                    permanent.addAbility(new IslandwalkAbility(), source.getSourceId(), game);
                    break;
                case SWAMP:
                    permanent.addAbility(new SwampwalkAbility(), source.getSourceId(), game);
                    break;
                case MOUNTAIN:
                    permanent.addAbility(new MountainwalkAbility(), source.getSourceId(), game);
                    break;
                case FOREST:
                    permanent.addAbility(new ForestwalkAbility(), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }
}
