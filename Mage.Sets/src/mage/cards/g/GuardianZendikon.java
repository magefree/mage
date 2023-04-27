
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GuardianZendikon extends CardImpl {

    public GuardianZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        // Enchanted land is a 2/6 white Wall creature with defender. It's still a land.
        // When enchanted land dies, return that card to its owner's hand.

        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new GuardianZendikonWallToken(), "Enchanted land is a 2/6 white Wall creature with defender. It's still a land", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);

        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false);
        this.addAbility(ability3);
    }

    private GuardianZendikon(final GuardianZendikon card) {
        super(card);
    }

    @Override
    public GuardianZendikon copy() {
        return new GuardianZendikon(this);
    }
}

class GuardianZendikonWallToken extends TokenImpl {

    GuardianZendikonWallToken() {
        super("", "2/6 white Wall creature with defender");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.WALL);
        power = new MageInt(2);
        toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
    }
    public GuardianZendikonWallToken(final GuardianZendikonWallToken token) {
        super(token);
    }

    public GuardianZendikonWallToken copy() {
        return new GuardianZendikonWallToken(this);
    }
}
