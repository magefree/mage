
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class GenjuOfTheFields extends CardImpl {

    private static final FilterLandPermanent FILTER = new FilterLandPermanent(SubType.PLAINS, "Plains");

    public GenjuOfTheFields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant Plains
        TargetPermanent auraTarget = new TargetLandPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {2}: Until end of turn, enchanted Plains becomes a 2/5 white Spirit creature with "Whenever this creature deals damage, its controller gains that much life." It's still a land.
        Effect effect = new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(new SpiritToken(),
                "Until end of turn, enchanted Plains becomes a 2/5 white Spirit creature", Duration.EndOfTurn);
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        effect = new GainAbilityAttachedEffect(new DealsDamageGainLifeSourceTriggeredAbility(), AttachmentType.AURA, Duration.EndOfTurn);
        effect.setText("with \"Whenever this creature deals damage, its controller gains that much life.\" It's still a land");
        ability2.addEffect(effect);
        this.addAbility(ability2);

        // When enchanted Plains is put into a graveyard, you may return Genju of the Fields from your graveyard to your hand.
        Effect effect2 = new ReturnToHandSourceEffect(false, true);
        effect2.setText("you may return {this} from your graveyard to your hand");
        Ability ability3 = new DiesAttachedTriggeredAbility(effect2, "enchanted Plains", true, false);
        this.addAbility(ability3);
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
        public SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
