package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class FearOfDeath extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CARDS, null)
    );

    public FearOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Fear of Death enters the battlefield, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // Enchanted creature gets -X/-0, where X is the number of cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(xValue, StaticValue.get(0))));
    }

    private FearOfDeath(final FearOfDeath card) {
        super(card);
    }

    @Override
    public FearOfDeath copy() {
        return new FearOfDeath(this);
    }
}
