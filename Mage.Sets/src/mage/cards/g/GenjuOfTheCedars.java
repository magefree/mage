package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GenjuOfTheCedars extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent(SubType.FOREST, "Forest");

    public GenjuOfTheCedars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant Forest
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        // {2}: Enchanted Forest becomes a 4/4 green Spirit creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(
                new SpiritToken(), "Enchanted Forest becomes a 4/4 green " +
                "Spirit creature until end of turn. It's still a land", Duration.EndOfTurn
        ), new GenericManaCost(2)));

        // When enchanted Forest is put into a graveyard, you may return Genju of the Cedars from your graveyard to your hand.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new ReturnToHandSourceEffect(false, true)
                        .setText("you may return {this} from your graveyard to your hand"),
                "enchanted Forest", true, false
        ));
    }

    private GenjuOfTheCedars(final GenjuOfTheCedars card) {
        super(card);
    }

    @Override
    public GenjuOfTheCedars copy() {
        return new GenjuOfTheCedars(this);
    }

    private static class SpiritToken extends TokenImpl {

        SpiritToken() {
            super("", "4/4 green Spirit creature");
            cardType.add(CardType.CREATURE);
            color.setGreen(true);
            subtype.add(SubType.SPIRIT);
            power = new MageInt(4);
            toughness = new MageInt(4);
        }

        private SpiritToken(final SpiritToken token) {
            super(token);
        }

        public SpiritToken copy() {
            return new SpiritToken(this);
        }
    }
}
