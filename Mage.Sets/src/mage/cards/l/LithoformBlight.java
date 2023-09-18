package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 * @author TheElk801
 */
public final class LithoformBlight extends CardImpl {

    public LithoformBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Lithoform Blight enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Enchanted land loses all land types and abilities and has "{T}: Add {C}" and "{T}, Pay 1 life: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new ChangeLandAttachedEffect()));
    }

    private LithoformBlight(final LithoformBlight card) {
        super(card);
    }

    @Override
    public LithoformBlight copy() {
        return new LithoformBlight(this);
    }
}

class ChangeLandAttachedEffect extends ContinuousEffectImpl {

    ChangeLandAttachedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AddAbility);
        staticText = "Enchanted land loses all land types and abilities " +
                "and has \"{T}: Add {C}\" and \"{T}, Pay 1 life: Add one mana of any color.\"";
    }

    private ChangeLandAttachedEffect(final ChangeLandAttachedEffect effect) {
        super(effect);
    }

    @Override
    public ChangeLandAttachedEffect copy() {
        return new ChangeLandAttachedEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return true;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                break;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                Ability ability = new AnyColorManaAbility();
                ability.addCost(new PayLifeCost(1));
                permanent.addAbility(ability, source.getSourceId(), game);
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.TypeChangingEffects_4;
    }
}
