package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NissasZendikon extends CardImpl {

    public NissasZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land is a 4/4 Elemental creature with reach and haste. It's still a land.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new NissasZendikonToken(), "Enchanted land is a 4/4 Elemental creature with reach and haste. It's still a land",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR
        )));

        // When enchanted land dies, return that card to its owner's hand.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new ReturnToHandAttachedEffect(), "enchanted land", false
        ));
    }

    private NissasZendikon(final NissasZendikon card) {
        super(card);
    }

    @Override
    public NissasZendikon copy() {
        return new NissasZendikon(this);
    }
}

class NissasZendikonToken extends TokenImpl {
    NissasZendikonToken() {
        super("", "4/4 Elemental creature with reach and haste");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(ReachAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    private NissasZendikonToken(final NissasZendikonToken token) {
        super(token);
    }

    public NissasZendikonToken copy() {
        return new NissasZendikonToken(this);
    }
}
