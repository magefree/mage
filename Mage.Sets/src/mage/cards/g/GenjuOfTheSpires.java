
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
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
public final class GenjuOfTheSpires extends CardImpl {

    private static final FilterLandPermanent FILTER = new FilterLandPermanent(SubType.MOUNTAIN, "Mountain");

    public GenjuOfTheSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        // Enchant Mountain
        TargetPermanent auraTarget = new TargetLandPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {2}: Enchanted Mountain becomes a 6/1 red Spirit creature until end of turn. It's still a land.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(new SpiritToken(), "Enchanted Mountain becomes a 6/1 red Spirit creature until end of turn. It's still a land", Duration.EndOfTurn), new GenericManaCost(2));
        this.addAbility(ability2);

        // When enchanted Mountain is put into a graveyard, you may return Genju of the Spires from your graveyard to your hand.
        Effect effect = new ReturnToHandSourceEffect(false, true);
        effect.setText("you may return {this} from your graveyard to your hand");
        Ability ability3 = new DiesAttachedTriggeredAbility(effect, "enchanted Mountain", true, false);
        this.addAbility(ability3);
    }

    private GenjuOfTheSpires(final GenjuOfTheSpires card) {
        super(card);
    }

    @Override
    public GenjuOfTheSpires copy() {
        return new GenjuOfTheSpires(this);
    }

    private static class SpiritToken extends TokenImpl {

        SpiritToken() {
            super("Spirit", "6/1 red Spirit creature");
            cardType.add(CardType.CREATURE);
            color.setRed(true);
            subtype.add(SubType.SPIRIT);
            power = new MageInt(6);
            toughness = new MageInt(1);
        }
        public SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
