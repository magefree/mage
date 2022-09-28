
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AwakenTheAncient extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.MOUNTAIN, "Mountain");

    public AwakenTheAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant Mountain
        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted Mountain is a 7/7 red Giant creature with haste. It's still a land.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new GiantToken(), "Enchanted Mountain is a 7/7 red Giant creature with haste. It's still a land", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);

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

        public GiantToken(final GiantToken token) {
            super(token);
        }

        public GiantToken copy() {
            return new GiantToken(this);
        }
    }
}
