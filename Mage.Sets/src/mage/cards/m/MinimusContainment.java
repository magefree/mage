package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinimusContainment extends CardImpl {

    public MinimusContainment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant nonland permanent
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent is a Treasure artifact with "{T}, Sacrifice this artifact: Add one mana of any color," and it loses all other abilities.
        this.addAbility(new SimpleStaticAbility(new MinimusContainmentEffect()));
    }

    private MinimusContainment(final MinimusContainment card) {
        super(card);
    }

    @Override
    public MinimusContainment copy() {
        return new MinimusContainment(this);
    }
}

class MinimusContainmentEffect extends ContinuousEffectImpl {

    private static final Ability ability = new AnyColorManaAbility();

    static {
        Cost cost = new SacrificeSourceCost();
        cost.setText("sacrifice this artifact");
        ability.addCost(cost);
    }

    MinimusContainmentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "enchanted permanent is a Treasure artifact with " +
                "\"{T}, Sacrifice this artifact: Add one mana of any color,\" and it loses all other abilities";
    }

    private MinimusContainmentEffect(final MinimusContainmentEffect effect) {
        super(effect);
    }

    @Override
    public MinimusContainmentEffect copy() {
        return new MinimusContainmentEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent aura = source.getSourcePermanentIfItStillExists(game);
        if (aura == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(aura.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.retainAllArtifactSubTypes(game);
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addSubType(game, SubType.TREASURE);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(ability, source.getSourceId(), game);
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
