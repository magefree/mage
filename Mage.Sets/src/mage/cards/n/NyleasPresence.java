
package mage.cards.n;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NyleasPresence extends CardImpl {

    public NyleasPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // When Nylea's Presence enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
        // Enchanted land is every basic land type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NyleasPresenceLandTypeEffect()));

    }

    public NyleasPresence(final NyleasPresence card) {
        super(card);
    }

    @Override
    public NyleasPresence copy() {
        return new NyleasPresence(this);
    }
}

class NyleasPresenceLandTypeEffect extends ContinuousEffectImpl {

    protected List<SubType> landTypes = new ArrayList<>();

    public NyleasPresenceLandTypeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        landTypes.addAll(SubType.getBasicLands(false));
        this.staticText = "Enchanted land is every basic land type in addition to its other types";
    }

    public NyleasPresenceLandTypeEffect(final NyleasPresenceLandTypeEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public NyleasPresenceLandTypeEffect copy() {
        return new NyleasPresenceLandTypeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        Mana mana = new Mana();
                        for (Ability ability : land.getAbilities()){
                            if (ability instanceof BasicManaAbility) {
                                for (Mana netMana: ((BasicManaAbility)ability ).getNetMana(game)) {
                                    mana.add(netMana);
                                }                                   
                            }
                        }
                        if (mana.getGreen() == 0 && landTypes.contains(SubType.FOREST)) {
                            land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getRed() == 0 && landTypes.contains(SubType.MOUNTAIN)) {
                            land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getBlue() == 0 && landTypes.contains(SubType.ISLAND)) {
                            land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getWhite() == 0 && landTypes.contains(SubType.PLAINS)) {
                            land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getBlack() == 0 && landTypes.contains(SubType.SWAMP)) {
                            land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case TypeChangingEffects_4:
                        for (SubType subtype : landTypes) {
                            if (!land.hasSubtype(subtype, game)) {
                                land.getSubtype(game).add(subtype);
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
