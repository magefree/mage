
package mage.cards.b;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class BludgeonBrawl extends CardImpl {

    public BludgeonBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Each noncreature, non-Equipment artifact is an Equipment with equip {X} and "Equipped creature gets +X/+0," where X is that artifact's converted mana cost.
        this.addAbility(new BludgeonBrawlAbility());
    }

    private BludgeonBrawl(final BludgeonBrawl card) {
        super(card);
    }

    @Override
    public BludgeonBrawl copy() {
        return new BludgeonBrawl(this);
    }
}

class BludgeonBrawlAbility extends StaticAbility {

    public BludgeonBrawlAbility() {
        super(Zone.BATTLEFIELD, new BludgeonBrawlAddSubtypeEffect());
        this.addEffect(new BludgeonBrawlGainAbilityEffect());
    }

    public BludgeonBrawlAbility(BludgeonBrawlAbility ability) {
        super(ability);
    }

    @Override
    public BludgeonBrawlAbility copy() {
        return new BludgeonBrawlAbility(this);
    }

    @Override
    public String getRule() {
        return "Each noncreature, non-Equipment artifact is an Equipment with equip {X} and \"Equipped creature gets +X/+0,\" where X is that artifact's mana value.";
    }
}

class BludgeonBrawlAddSubtypeEffect extends ContinuousEffectImpl {

    public BludgeonBrawlAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    public BludgeonBrawlAddSubtypeEffect(final BludgeonBrawlAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterArtifactPermanent filter = new FilterArtifactPermanent("noncreature, non-Equipment artifact");
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(SubType.EQUIPMENT.getPredicate()));

        Cards affectedPermanents = new CardsImpl();
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.addSubType(game, SubType.EQUIPMENT);
                affectedPermanents.add(permanent.getId());
            }
        }
        game.getState().setValue(source.getSourceId() + "BludgeonBrawlAffectedPermanents", affectedPermanents);
        return true;
    }

    @Override
    public BludgeonBrawlAddSubtypeEffect copy() {
        return new BludgeonBrawlAddSubtypeEffect(this);
    }
}

class BludgeonBrawlGainAbilityEffect extends ContinuousEffectImpl {

    public BludgeonBrawlGainAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public BludgeonBrawlGainAbilityEffect(final BludgeonBrawlGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public BludgeonBrawlGainAbilityEffect copy() {
        return new BludgeonBrawlGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards permanents = (Cards) game.getState().getValue(source.getSourceId() + "BludgeonBrawlAffectedPermanents");
        if (permanents != null) {
            for (UUID permanentId : permanents) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    int manaValue = permanent.getManaValue();
                    permanent.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(manaValue)), source.getSourceId(), game);
                    permanent.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(manaValue, 0)), source.getSourceId(), game);
                }
            }
            return true;
        }

        return false;
    }
}
