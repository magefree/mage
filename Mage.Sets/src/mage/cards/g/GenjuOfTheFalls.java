
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
import mage.abilities.keyword.FlyingAbility;
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
public final class GenjuOfTheFalls extends CardImpl {

    private static final FilterLandPermanent FILTER = new FilterLandPermanent(SubType.ISLAND, "Island");

    public GenjuOfTheFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);

        // Enchant Island
        TargetPermanent auraTarget = new TargetLandPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {2}: Enchanted Island becomes a 3/2 blue Spirit creature with flying until end of turn. It's still a land.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(new SpiritToken(), "Enchanted Island becomes a 3/2 blue Spirit creature with flying until end of turn. It's still a land", Duration.EndOfTurn), new GenericManaCost(2));
        this.addAbility(ability2);

        // When enchanted Island is put into a graveyard, you may return Genju of the Falls from your graveyard to your hand.        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        Effect effect = new ReturnToHandSourceEffect(false, true);
        effect.setText("you may return {this} from your graveyard to your hand");
        Ability ability3 = new DiesAttachedTriggeredAbility(effect, "enchanted Island", true, false);
        this.addAbility(ability3);
    }

    private GenjuOfTheFalls(final GenjuOfTheFalls card) {
        super(card);
    }

    @Override
    public GenjuOfTheFalls copy() {
        return new GenjuOfTheFalls(this);
    }

    private static class SpiritToken extends TokenImpl {

        SpiritToken() {
            super("Spirit", "3/2 blue Spirit creature with flying");
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            subtype.add(SubType.SPIRIT);
            power = new MageInt(3);
            toughness = new MageInt(2);
            addAbility(FlyingAbility.getInstance());
        }

        public SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
