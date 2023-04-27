
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class GenjuOfTheFens extends CardImpl {

    private static final FilterLandPermanent FILTER = new FilterLandPermanent(SubType.SWAMP, "Swamp");

    public GenjuOfTheFens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);

        // Enchant Swamp
        TargetPermanent auraTarget = new TargetLandPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {2}: Until end of turn, enchanted Swamp becomes a 2/2 black Spirit creature with "{B}: This creature gets +1/+1 until end of turn." It's still a land.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(new SpiritToken(), "Until end of turn, enchanted Swamp becomes a 2/2 black Spirit creature with \"{B}: This creature gets +1/+1 until end of turn.\" It's still a land", Duration.EndOfTurn), new GenericManaCost(2));
        this.addAbility(ability2);

        // When enchanted Swamp is put into a graveyard, you may return Genju of the Fens from your graveyard to your hand.
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandSourceEffect(false, true), "enchanted Swamp", true, false);
        this.addAbility(ability3);
    }

    private GenjuOfTheFens(final GenjuOfTheFens card) {
        super(card);
    }

    @Override
    public GenjuOfTheFens copy() {
        return new GenjuOfTheFens(this);
    }

    private static class SpiritToken extends TokenImpl {

        SpiritToken() {
            super("Spirit", "2/2 black Spirit creature with \"{B}: This creature gets +1/+1 until end of turn.\"");
            cardType.add(CardType.CREATURE);
            color.setBlack(true);
            subtype.add(SubType.SPIRIT);
            power = new MageInt(2);
            toughness = new MageInt(2);
            addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
        }

        public SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
