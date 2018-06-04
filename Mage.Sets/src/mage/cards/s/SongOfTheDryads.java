
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class SongOfTheDryads extends CardImpl {

    public SongOfTheDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent is a colorless Forest land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesColorlessForestLandEffect()));

    }

    public SongOfTheDryads(final SongOfTheDryads card) {
        super(card);
    }

    @Override
    public SongOfTheDryads copy() {
        return new SongOfTheDryads(this);
    }
}

class BecomesColorlessForestLandEffect extends ContinuousEffectImpl {

    public BecomesColorlessForestLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Enchanted permanent is a colorless Forest land";
    }

    public BecomesColorlessForestLandEffect(final BecomesColorlessForestLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesColorlessForestLandEffect copy() {
        return new BecomesColorlessForestLandEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setWhite(false);
                        permanent.getColor(game).setGreen(false);
                        permanent.getColor(game).setBlack(false);
                        permanent.getColor(game).setBlue(false);
                        permanent.getColor(game).setRed(false);
                        break;
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        break;
                    case TypeChangingEffects_4:
                        permanent.getCardType().clear();
                        permanent.addCardType(CardType.LAND);
                        permanent.getSubtype(game).clear();
                        permanent.getSubtype(game).add(SubType.FOREST);
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}
