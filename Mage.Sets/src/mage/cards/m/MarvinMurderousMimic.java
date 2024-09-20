package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
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
 * @author TheElk801
 */
public final class MarvinMurderousMimic extends CardImpl {

    public MarvinMurderousMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TOY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Marvin, Murderous Mimic has all activated abilities of creatures you control that don't have the same name as this creature.
        this.addAbility(new SimpleStaticAbility(new MarvinMurderousMimicEffect()));
    }

    private MarvinMurderousMimic(final MarvinMurderousMimic card) {
        super(card);
    }

    @Override
    public MarvinMurderousMimic copy() {
        return new MarvinMurderousMimic(this);
    }
}

class MarvinMurderousMimicEffect extends ContinuousEffectImpl {

    MarvinMurderousMimicEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of creatures " +
                "you control that don't have the same name as this creature";
    }

    private MarvinMurderousMimicEffect(final MarvinMurderousMimicEffect effect) {
        super(effect);
    }

    @Override
    public MarvinMurderousMimicEffect copy() {
        return new MarvinMurderousMimicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        List<Ability> abilities = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(p -> !p.sharesName(permanent, game))
                .map(p -> p.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(Ability::isActivatedAbility)
                .collect(Collectors.toList());
        for (Ability ability : abilities) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }
}
