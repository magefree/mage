package mage.cards.n;

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

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NyleasPresence extends CardImpl {

    public NyleasPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Nylea's Presence enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Enchanted land is every basic land type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new NyleasPresenceLandTypeEffect()));
    }

    private NyleasPresence(final NyleasPresence card) {
        super(card);
    }

    @Override
    public NyleasPresence copy() {
        return new NyleasPresence(this);
    }
}

class NyleasPresenceLandTypeEffect extends ContinuousEffectImpl {

    public NyleasPresenceLandTypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "Enchanted land is every basic land type in addition to its other types";
        dependencyTypes.add(DependencyType.BecomePlains);
        dependencyTypes.add(DependencyType.BecomeIsland);
        dependencyTypes.add(DependencyType.BecomeSwamp);
        dependencyTypes.add(DependencyType.BecomeMountain);
        dependencyTypes.add(DependencyType.BecomeForest);
    }

    public NyleasPresenceLandTypeEffect(final NyleasPresenceLandTypeEffect effect) {
        super(effect);
    }

    @Override
    public NyleasPresenceLandTypeEffect copy() {
        return new NyleasPresenceLandTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return true;
        }
        Permanent land = game.getPermanent(enchantment.getAttachedTo());
        if (land == null) {
            return true;
        }
        land.addSubType(game,SubType.getBasicLands());
        land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
        land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
        land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
        return true;
    }
}
