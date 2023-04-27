package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class GiftOfFangs extends CardImpl {

    public GiftOfFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 as long as it's a Vampire. Otherwise, it gets -2/-2.
        this.addAbility(new SimpleStaticAbility(new GiftOfFangsEffect()));
    }

    private GiftOfFangs(final GiftOfFangs card) {
        super(card);
    }

    @Override
    public GiftOfFangs copy() {
        return new GiftOfFangs(this);
    }
}

class GiftOfFangsEffect extends ContinuousEffectImpl {

    public GiftOfFangsEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.Neutral);
        staticText = "Enchanted creature gets +2/+2 as long as it's a Vampire. Otherwise, it gets -2/-2";
    }

    private GiftOfFangsEffect(final GiftOfFangsEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfFangsEffect copy() {
        return new GiftOfFangsEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            // Added boosts of activated or triggered abilities exist independent from the source they are created by
            // so a continuous effect for the permanent itself with the attachment is created
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (affectedObjectsSet) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanent(equipment.getAttachedTo());
            }
        }
        if (permanent != null) {
            if (permanent.hasSubtype(SubType.VAMPIRE, game)) {
                permanent.addPower(2);
                permanent.addToughness(2);
            } else {
                permanent.addPower(-2);
                permanent.addToughness(-2);
            }
        }
        return true;
    }
}
