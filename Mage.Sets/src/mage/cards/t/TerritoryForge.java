package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TerritoryForge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.LAND.getPredicate()));
    }

    public TerritoryForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R}");

        // When Territory Forge enters the battlefield, if you cast it, exile target artifact or land.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true)),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, exile target artifact or land."
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Territory Forge has all activated abilities of the exiled card.
        this.addAbility(new SimpleStaticAbility(new TerritoryForgeStaticEffect()));
    }

    private TerritoryForge(final TerritoryForge card) {
        super(card);
    }

    @Override
    public TerritoryForge copy() {
        return new TerritoryForge(this);
    }
}

// Inspired by Dark Impostor
class TerritoryForgeStaticEffect extends ContinuousEffectImpl {

    TerritoryForgeStaticEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} has all activated abilities of the exiled card";
    }

    private TerritoryForgeStaticEffect(final TerritoryForgeStaticEffect effect) {
        super(effect);
    }

    @Override
    public TerritoryForgeStaticEffect copy() {
        return new TerritoryForgeStaticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), permanent.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        for (Card card : exileZone.getCards(game)) {
            for (Ability ability : card.getAbilities(game)) {
                if (ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.MANA) {
                    ActivatedAbility copyAbility = (ActivatedAbility) ability.copy();
                    permanent.addAbility(copyAbility, source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
}