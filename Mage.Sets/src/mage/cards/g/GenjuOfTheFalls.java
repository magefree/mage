
package mage.cards.g;

import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GenjuOfTheFalls extends CardImpl {

    private static final FilterPermanent FILTER = new FilterPermanent(SubType.ISLAND, "Island");

    public GenjuOfTheFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        // Enchant Island
        TargetPermanent auraTarget = new TargetPermanent(FILTER);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        // {2}: Enchanted Island becomes a 3/2 blue Spirit creature with flying until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(
                new CreatureToken(3, 2, "3/2 blue Spirit creature with flying", SubType.SPIRIT).withColor("U").withAbility(FlyingAbility.getInstance()),
                "Enchanted Island becomes a 3/2 blue Spirit creature with flying until end of turn. It's still a land",
                Duration.EndOfTurn
            ),
            new GenericManaCost(2)
        ));

        // When enchanted Island is put into a graveyard, you may return Genju of the Falls from your graveyard to your hand.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new ReturnToHandSourceEffect(false, true)
                        .setText("you may return {this} from your graveyard to your hand"),
                "enchanted Island", true, false
        ));
    }

    private GenjuOfTheFalls(final GenjuOfTheFalls card) {
        super(card);
    }

    @Override
    public GenjuOfTheFalls copy() {
        return new GenjuOfTheFalls(this);
    }
}
