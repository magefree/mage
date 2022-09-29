

package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Oakenform extends CardImpl {

    public Oakenform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
    this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
    Ability ability = new EnchantAbility(auraTarget);
    this.addAbility(ability);
    this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OakenformEffect()));
    }

    private Oakenform(final Oakenform card) {
        super(card);
    }

    @Override
    public Oakenform copy() {
        return new Oakenform(this);
    }

}

class OakenformEffect extends ContinuousEffectImpl {

    public OakenformEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Enchanted creature gets +3/+3";
    }

    public OakenformEffect(final OakenformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability ablt) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
    Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (creature != null) {
            switch (layer) {
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.ModifyPT_7c) {
                        creature.addPower(3);
                        creature.addToughness(3);
                    }
                    break;
            }
            return true;
        }
    }
    return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
    return layer == Layer.PTChangingEffects_7;
    }

    @Override
    public OakenformEffect copy() {
        return new OakenformEffect(this);
    }

}
