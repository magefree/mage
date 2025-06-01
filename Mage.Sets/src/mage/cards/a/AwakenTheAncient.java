package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
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
 * @author jeffwadsworth
 */
public final class AwakenTheAncient extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MOUNTAIN, "Mountain");

    public AwakenTheAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant Mountain
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted Mountain is a 7/7 red Giant creature with haste. It's still a land.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new GiantToken(), "Enchanted Mountain is a 7/7 red Giant creature with haste. It's still a land",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR
        )));
    }

    private AwakenTheAncient(final AwakenTheAncient card) {
        super(card);
    }

    @Override
    public AwakenTheAncient copy() {
        return new AwakenTheAncient(this);
    }

    private static class GiantToken extends TokenImpl {

        GiantToken() {
            super("Giant", "7/7 red Giant creature with haste");
            cardType.add(CardType.CREATURE);
            color.setRed(true);
            subtype.add(SubType.GIANT);
            power = new MageInt(7);
            toughness = new MageInt(7);
            this.addAbility(HasteAbility.getInstance());
        }

        private GiantToken(final GiantToken token) {
            super(token);
        }

        public GiantToken copy() {
            return new GiantToken(this);
        }
    }
}
