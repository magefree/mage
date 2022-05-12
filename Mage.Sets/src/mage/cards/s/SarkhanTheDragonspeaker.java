
package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.emblems.SarkhanTheDragonspeakerEmblem;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * import mage.game.command.emblems.SarkhanTheDragonspeakerEmblem;
 *
 * @author emerald000
 */
public final class SarkhanTheDragonspeaker extends CardImpl {

    public SarkhanTheDragonspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);

        this.setStartingLoyalty(4);

        // +1: Until end of turn, Sarkhan, the Dragonspeaker becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.
        this.addAbility(new LoyaltyAbility(new SarkhanTheDragonspeakerEffect(), 1));

        // -3: Sarkhan, the Dragonspeaker deals 4 damage to target creature.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(4), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -6: You get an emblem with "At the beginning of your draw step, draw two additional cards" and "At the beginning of your end step, discard your hand."
        Effect effect = new GetEmblemEffect(new SarkhanTheDragonspeakerEmblem());
        effect.setText("You get an emblem with \"At the beginning of your draw step, draw two additional cards\" and \"At the beginning of your end step, discard your hand.\"");
        this.addAbility(new LoyaltyAbility(effect, -6));
    }

    private SarkhanTheDragonspeaker(final SarkhanTheDragonspeaker card) {
        super(card);
    }

    @Override
    public SarkhanTheDragonspeaker copy() {
        return new SarkhanTheDragonspeaker(this);
    }
}

class SarkhanTheDragonspeakerEffect extends ContinuousEffectImpl {

    SarkhanTheDragonspeakerEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Until end of turn, {this} becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.";
    }

    SarkhanTheDragonspeakerEffect(final SarkhanTheDragonspeakerEffect effect) {
        super(effect);
    }

    @Override
    public SarkhanTheDragonspeakerEffect copy() {
        return new SarkhanTheDragonspeakerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.removeAllCardTypes(game);
                        permanent.addCardType(game, CardType.CREATURE);
                        permanent.removeAllSubTypes(game);
                        permanent.addSubType(game, SubType.DRAGON);
                        permanent.getSuperType().clear();
                        permanent.addSuperType(SuperType.LEGENDARY);
                    }
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(ObjectColor.RED);
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                        permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
                        permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(4);
                        permanent.getToughness().setValue(4);
                    }
            }
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
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}
