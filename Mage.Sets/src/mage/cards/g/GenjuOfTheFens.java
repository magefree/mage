package mage.cards.g;

import mage.MageInt;
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
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GenjuOfTheFens extends CardImpl {

    private static final FilterPermanent FILTER = new FilterPermanent(SubType.SWAMP, "Swamp");

    public GenjuOfTheFens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.subtype.add(SubType.AURA);

        // Enchant Swamp
        TargetPermanent auraTarget = new TargetPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        // {2}: Until end of turn, enchanted Swamp becomes a 2/2 black Spirit creature with "{B}: This creature gets +1/+1 until end of turn." It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(
                new SpiritToken(), "Until end of turn, enchanted Swamp becomes a 2/2 black Spirit creature " +
                "with \"{B}: This creature gets +1/+1 until end of turn.\" It's still a land", Duration.EndOfTurn
        ), new GenericManaCost(2)));

        // When enchanted Swamp is put into a graveyard, you may return Genju of the Fens from your graveyard to your hand.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new ReturnToHandSourceEffect(false, true)
                        .setText("you may return {this} from your graveyard to your hand"),
                "enchanted Swamp", true, false
        ));
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
            addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
        }

        private SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
