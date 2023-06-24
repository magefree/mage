

package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki, North
 */
public final class ClawsOfValakut extends CardImpl {

     private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ClawsOfValakut (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");
        this.subtype.add(SubType.AURA);


        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(new PermanentsOnBattlefieldCount(filter, 1),
                new PermanentsOnBattlefieldCount(filter, 0),
                Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA).setText("and has first strike"));
        this.addAbility(ability);
    }

    public ClawsOfValakut (final ClawsOfValakut card) {
        super(card);
    }

    @Override
    public ClawsOfValakut copy() {
        return new ClawsOfValakut(this);
    }

}
