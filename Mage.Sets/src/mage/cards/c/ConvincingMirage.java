package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class ConvincingMirage extends CardImpl {

    public ConvincingMirage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));

        // As Convincing Mirage enters the battlefield, choose a basic land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.Neutral)));

        // Enchanted land is the chosen type.
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConvincingMirageContinousEffect()));
    }

    private ConvincingMirage(final ConvincingMirage card) {
        super(card);
    }

    @Override
    public ConvincingMirage copy() {
        return new ConvincingMirage(this);
    }
}

class ConvincingMirageContinousEffect extends ContinuousEffectImpl {

    public ConvincingMirageContinousEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "Enchanted land is the chosen type";
    }

    public ConvincingMirageContinousEffect(final ConvincingMirageContinousEffect effect) {
        super(effect);
    }

    @Override
    public ConvincingMirageContinousEffect copy() {
        return new ConvincingMirageContinousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        switch (choice) {
            case FOREST:
                dependencyTypes.add(DependencyType.BecomeForest);
                break;
            case PLAINS:
                dependencyTypes.add(DependencyType.BecomePlains);
                break;
            case MOUNTAIN:
                dependencyTypes.add(DependencyType.BecomeMountain);
                break;
            case ISLAND:
                dependencyTypes.add(DependencyType.BecomeIsland);
                break;
            case SWAMP:
                dependencyTypes.add(DependencyType.BecomeSwamp);
                break;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (enchantment == null || enchantment.getAttachedTo() == null || choice == null) {
            return false;
        }
        Permanent land = game.getPermanent(enchantment.getAttachedTo());
        if (land == null) {
            return false;
        }
        land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
        land.addSubType(game, choice);
        land.removeAllAbilities(source.getSourceId(), game);
        switch (choice) {
            case FOREST:
                land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                break;
            case PLAINS:
                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                break;
            case MOUNTAIN:
                land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                break;
            case ISLAND:
                land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                break;
            case SWAMP:
                land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                break;
        }
        return true;
    }
}
