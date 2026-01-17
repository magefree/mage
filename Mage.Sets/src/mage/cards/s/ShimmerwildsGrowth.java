package mage.cards.s;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShimmerwildsGrowth extends CardImpl {

    public ShimmerwildsGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // As this Aura enters, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Enchanted land is the chosen color.
        this.addAbility(new SimpleStaticAbility(new ShimmerwildsGrowthColorEffect()));

        // Whenever enchanted land is tapped for mana, its controller adds an additional one mana of the chosen color.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new ShimmerwildsGrowthManaEffect(), "land"));
    }

    private ShimmerwildsGrowth(final ShimmerwildsGrowth card) {
        super(card);
    }

    @Override
    public ShimmerwildsGrowth copy() {
        return new ShimmerwildsGrowth(this);
    }
}

class ShimmerwildsGrowthColorEffect extends ContinuousEffectImpl {

    ShimmerwildsGrowthColorEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "enchanted land is the chosen color";
    }

    private ShimmerwildsGrowthColorEffect(final ShimmerwildsGrowthColorEffect effect) {
        super(effect);
    }

    @Override
    public ShimmerwildsGrowthColorEffect copy() {
        return new ShimmerwildsGrowthColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (permanent == null || color == null) {
            return false;
        }
        permanent.getColor().setColor(color);
        return true;
    }
}

class ShimmerwildsGrowthManaEffect extends ManaEffect {

    ShimmerwildsGrowthManaEffect() {
        super();
        staticText = "its controller adds an additional one mana of the chosen color";
    }

    private ShimmerwildsGrowthManaEffect(final ShimmerwildsGrowthManaEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getPermanent)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .orElse(null);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color == null) {
            return new Mana();
        }
        return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
    }

    @Override
    public ShimmerwildsGrowthManaEffect copy() {
        return new ShimmerwildsGrowthManaEffect(this);
    }
}
