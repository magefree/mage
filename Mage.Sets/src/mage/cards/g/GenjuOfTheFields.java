package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GenjuOfTheFields extends CardImpl {

    private static final FilterPermanent FILTER = new FilterPermanent(SubType.PLAINS, "Plains");

    public GenjuOfTheFields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant Plains
        TargetPermanent auraTarget = new TargetPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        // {2}: Until end of turn, enchanted Plains becomes a 2/5 white Spirit creature with "Whenever this creature deals damage, its controller gains that much life." It's still a land.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(
                new SpiritToken(), "Until end of turn, enchanted Plains " +
                "becomes a 2/5 white Spirit creature", Duration.EndOfTurn
        ), new GenericManaCost(2));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsDamageSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH)), AttachmentType.AURA, Duration.EndOfTurn
        ).setText("with \"Whenever this creature deals damage, its controller gains that much life.\" It's still a land"));
        this.addAbility(ability);

        // When enchanted Plains is put into a graveyard, you may return Genju of the Fields from your graveyard to your hand.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new ReturnToHandSourceEffect(false, true)
                        .setText("you may return {this} from your graveyard to your hand"),
                "enchanted Plains", true, false
        ));
    }

    private GenjuOfTheFields(final GenjuOfTheFields card) {
        super(card);
    }

    @Override
    public GenjuOfTheFields copy() {
        return new GenjuOfTheFields(this);
    }

    private static class SpiritToken extends TokenImpl {

        SpiritToken() {
            super("Spirit", "2/5 white Spirit creature");
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            subtype.add(SubType.SPIRIT);
            power = new MageInt(2);
            toughness = new MageInt(5);
        }

        private SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
