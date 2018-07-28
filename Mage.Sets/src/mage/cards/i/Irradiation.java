package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.condition.common.EquippedHasSupertypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
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
import mage.util.SubTypeList;

/**
 *
 * @author NinthWorld
 */
public final class Irradiation extends CardImpl {

    public Irradiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // If enchanted creature is an artifact, it gets +2/+0. Otherwise, it gets +2/-2.
        Condition condition = new EquippedHasTypeCondition(CardType.ARTIFACT);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, 0),
                condition,
                "If enchanted creature is an artifact, it gets +2/+0")));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, -2),
                new InvertCondition(condition),
                "Otherwise, it gets +2/-2")));
    }

    public Irradiation(final Irradiation card) {
        super(card);
    }

    @Override
    public Irradiation copy() {
        return new Irradiation(this);
    }
}

class EquippedHasTypeCondition implements Condition {

    private CardType type;

    public EquippedHasTypeCondition(CardType type){
        this.type = type;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
            if (attachedTo == null) {
                attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (attachedTo != null) {
                return attachedTo.getCardType().contains(type);
            }
        }
        return false;
    }
}