package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiftOfTheWoods extends CardImpl {

    public GiftOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getSpellAbility().addTarget(auraTarget);
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature blocks or becomes blocked, it gets +0/+3 until end of turn and you gain 1 life.
        Ability ability = new BlocksOrBlockedAttachedTriggeredAbility(new BoostTargetEffect(0, 3).setText("it gets +0/+3 until end of turn"));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private GiftOfTheWoods(final GiftOfTheWoods card) {
        super(card);
    }

    @Override
    public GiftOfTheWoods copy() {
        return new GiftOfTheWoods(this);
    }
}
